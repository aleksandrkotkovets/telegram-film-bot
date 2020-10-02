package com.telegram.film_bot.data.repository;

import com.telegram.film_bot.data.entities.RecommendFilm;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RecommendFilmRepository extends MongoRepository<RecommendFilm, String> {

    List<RecommendFilm> findAllByReadFalse();

}
