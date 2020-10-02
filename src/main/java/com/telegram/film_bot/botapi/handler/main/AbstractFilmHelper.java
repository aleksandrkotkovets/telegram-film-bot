package com.telegram.film_bot.botapi.handler.main;

import com.telegram.film_bot.data.entities.Film;
import com.telegram.film_bot.data.entities.RecommendFilm;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractFilmHelper {

    public List<Integer> getIdList(List<Film> films) {
        return films.stream()
                .map(Film::getMessageId)
                .map(Math::toIntExact)
                .collect(Collectors.toList());
    }

    public List<String> getMessageList(List<RecommendFilm> recommendFilms) {
        return recommendFilms.stream()
                .map(RecommendFilm::getMessageText)
                .collect(Collectors.toList());
    }

}
