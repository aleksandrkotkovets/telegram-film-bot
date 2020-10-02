package com.telegram.film_bot.utils;

import com.telegram.film_bot.data.entities.RecommendFilm;
import com.telegram.film_bot.data.repository.RecommendFilmRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class AsyncMethod {

    private final RecommendFilmRepository recommendFilmRepository;

    public AsyncMethod(RecommendFilmRepository recommendFilmRepository) {
        this.recommendFilmRepository = recommendFilmRepository;
    }

    @Async
    @Transactional
    public void saveAll(List<RecommendFilm> films) {
        recommendFilmRepository.saveAll(films);
    }
}
