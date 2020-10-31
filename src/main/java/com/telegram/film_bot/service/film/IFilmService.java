package com.telegram.film_bot.service.film;

import com.telegram.film_bot.botapi.handler.callbackquery.CallbackQueryType;
import com.telegram.film_bot.data.entities.Film;
import com.telegram.film_bot.data.entities.RecommendFilm;

import java.util.List;

public interface IFilmService {
    List<Film> findFilmByMessageTextIncludeCriteria(String text);

    Film getRandomFilm();

    Film getRandomFilmByParam(String param);

    List<String> getUniqParamList(CallbackQueryType callbackQueryType);

    List<Film> findTopFilmByCount(Integer countTopFilms);

    List<RecommendFilm> getUnReadRecommendations();

    List<RecommendFilm> getAllRecommendations();

    RecommendFilm deleteRecommendFilmByMessageId(Integer messageId);
}
