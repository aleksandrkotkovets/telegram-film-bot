package com.telegram.film_bot.service.film;


import org.telegram.telegrambots.meta.api.objects.Message;

public interface IRecommendFilmService {
    void saveRecommendedFilm(Message message);
}
