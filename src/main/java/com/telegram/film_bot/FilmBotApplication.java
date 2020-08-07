package com.telegram.film_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class FilmBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilmBotApplication.class, args);
    }

}
