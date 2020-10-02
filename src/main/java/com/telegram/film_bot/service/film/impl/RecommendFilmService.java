package com.telegram.film_bot.service.film.impl;

import com.telegram.film_bot.data.entities.RecommendFilm;
import com.telegram.film_bot.data.repository.RecommendFilmRepository;
import com.telegram.film_bot.service.film.IRecommendFilmService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class RecommendFilmService implements IRecommendFilmService {

    private RecommendFilmRepository recommendFilmRepository;

    public RecommendFilmService(RecommendFilmRepository recommendFilmRepository) {
        this.recommendFilmRepository = recommendFilmRepository;
    }

    @Async
    @Override
    @Transactional
    public void saveRecommendedFilm(Message message) {
        RecommendFilm recommendFilm = new RecommendFilm();
        recommendFilm.setUserId(message.getFrom().getId());
        recommendFilm.setChatId(message.getChatId());
        recommendFilm.setMessageId(message.getMessageId());
        recommendFilm.setMessageText(message.getText());
        recommendFilmRepository.save(recommendFilm);
    }
}
