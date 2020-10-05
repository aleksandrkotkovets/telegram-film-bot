package com.telegram.film_bot.service.film.impl;

import com.telegram.film_bot.botapi.handler.callbackquery.CallbackQueryType;
import com.telegram.film_bot.data.entities.Film;
import com.telegram.film_bot.data.entities.RecommendFilm;
import com.telegram.film_bot.data.repository.FilmRepository;
import com.telegram.film_bot.data.repository.RecommendFilmRepository;
import com.telegram.film_bot.service.film.IFilmService;
import com.telegram.film_bot.utils.AsyncMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.telegram.film_bot.utils.StringHelper.containsWords;
import static com.telegram.film_bot.utils.StringHelper.getUniqParams;
import static com.telegram.film_bot.utils.StringHelper.getUniqParamsWithSplit;
import static com.telegram.film_bot.utils.StringHelper.sortStringHowIntList;

@Service
public class FilmService implements IFilmService {

    @Value("${regex.message.get.year}")
    String REGEX_FIND_ALL_YEAR;

    @Value("${regex.message.get.rate}")
    String REGEX_FIND_ALL_RATE;

    @Value("${regex.message.get.genre}")
    String REGEX_FIND_ALL_GENRE;

    private final FilmRepository filmRepository;
    private final RecommendFilmRepository recommendFilmRepository;
    private final AsyncMethod asyncMethod;

    public FilmService(FilmRepository filmRepository,
                       RecommendFilmRepository recommendFilmRepository,
                       AsyncMethod asyncMethod) {
        this.filmRepository = filmRepository;
        this.recommendFilmRepository = recommendFilmRepository;
        this.asyncMethod = asyncMethod;
    }

    @Override
    public List<Film> findFilmByMessageTextIncludeCriteria(String criteria) {
        List<Film> allFilms = filmRepository.findAll();
        return getFilmsByCriteria(criteria, allFilms);
    }

    @Override
    public Film getRandomFilm() {
        List<Film> allFilms = filmRepository.findAll();
        return getRandomFilm(allFilms);
    }


    @Override
    public Film getRandomFilmByParam(String param) {
        List<Film> films = findFilmByMessageTextIncludeCriteria(param);
        return getRandomFilm(films);
    }

    @Override
    public List<String> getUniqParamList(CallbackQueryType callbackQueryType) {
        List<String> params = new ArrayList<>();
        List<Film> filmList = filmRepository.findAll();
        List<String> messageList = getMessageList(filmList);
        switch (callbackQueryType) {
            case RATE: {
                List<String> uniqParams = getUniqParams(messageList, REGEX_FIND_ALL_RATE);
                params = sortStringHowIntList(uniqParams)
                        .subList(uniqParams.size() - 6, uniqParams.size())
                        .stream()
                        .map(rate -> rate + '+')
                        .collect(Collectors.toList());
                break;
            }
            case YEAR: {
                List<String> uniqParams = getUniqParams(messageList, REGEX_FIND_ALL_YEAR);
                params = sortStringHowIntList(uniqParams);
                break;
            }
            case GENRE: {
                params = getUniqParamsWithSplit(messageList, REGEX_FIND_ALL_GENRE);
                break;
            }
        }
        return params;
    }

    @Override
    public List<Film> findTopFilmByCount(Integer countTopFilms) {
        List<Film> filmList = filmRepository.findAll();

        return getTopFilms(countTopFilms, filmList);
    }

    @Override
    @Transactional
    public synchronized List<RecommendFilm> getUnReadRecommendations() {
        List<RecommendFilm> allByReadIsFalse = recommendFilmRepository.findAllByReadFalse();
        allByReadIsFalse.forEach(RecommendFilm::setRead);
        asyncMethod.saveAll(allByReadIsFalse);
        return allByReadIsFalse;
    }

    @Override
    public synchronized List<RecommendFilm> getAllRecommendations() {
        return recommendFilmRepository.findAll();
    }


    private List<Film> getTopFilms(Integer countTopFilms, List<Film> filmList) {
        List<Film> topFilms = new ArrayList<>();
        if (checkCount(filmList.size(), countTopFilms)) {
            getTopFilmList(countTopFilms, filmList, topFilms);
        } else {
            // with rate 10
            topFilms = Collections.singletonList(getRandomFilmByParam("10"));
        }
        return topFilms;
    }

    private void getTopFilmList(Integer countTopFilms, List<Film> filmList, List<Film> topFilms) {
        List<String> rateList = getUniqParamList(CallbackQueryType.RATE);
        for (int rateIndex = rateList.size() - 1; topFilms.size() < countTopFilms && rateIndex > 0; ) {
            List<Film> filmsByCriteria = getFilmsByCriteria(rateList.get(rateIndex), filmList);
            int delta = countTopFilms - topFilms.size();
            addAll(topFilms, filmsByCriteria, delta);
            rateIndex--;
        }
    }

    private void addAll(List<Film> to, List<Film> from, int delta) {
        Collections.shuffle(from);
        if (from.size() <= delta) {
            to.addAll(from);
        } else {
            to.addAll(from.stream().limit(delta).collect(Collectors.toList()));
        }
    }

    private boolean checkCount(int size, Integer countTopFilms) {
        return size >= countTopFilms && countTopFilms > 0;
    }

    private List<String> getMessageList(List<Film> filmList) {
        return getMessage(filmList);
    }

    private List<Film> getFilmsByCriteria(String criteria, List<Film> allFilms) {
        return allFilms.stream().map((film) -> containsWords(film.getMessageText(), criteria) ? film : null).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<String> getMessage(List<Film> filmList) {
        return filmList.stream()
                .map(Film::getMessageText)
                .collect(Collectors.toList());
    }

    private Film getRandomFilm(List<Film> allFilms) {
        Random rand = new Random();
        AtomicReference<Film> randomElement = new AtomicReference<>(new Film());
        Optional.of(allFilms)
                .ifPresent(list -> {
                    randomElement.set(list.get(rand.nextInt(list.size())));
                });
        return randomElement.get();
    }

}
