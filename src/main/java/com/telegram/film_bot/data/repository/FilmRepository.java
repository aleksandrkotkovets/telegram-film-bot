package com.telegram.film_bot.data.repository;

import com.telegram.film_bot.data.entities.Film;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FilmRepository extends MongoRepository<Film, String> {
}
