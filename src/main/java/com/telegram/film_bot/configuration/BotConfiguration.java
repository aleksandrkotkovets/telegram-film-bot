package com.telegram.film_bot.configuration;

import com.telegram.film_bot.FilmTelegramBot;
import com.telegram.film_bot.botapi.TelegramFacade;
import com.telegram.film_bot.configuration.properties.BotProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotConfiguration {

    private BotProperties botProperties;

    public BotConfiguration(BotProperties botProperties) {
        this.botProperties = botProperties;
    }

    @Bean
    public FilmTelegramBot filmTelegramBot(TelegramFacade telegramFacade) {
        FilmTelegramBot filmTelegramBot = new FilmTelegramBot(telegramFacade);
        filmTelegramBot.setBotUsername(botProperties.getBotUserName());
        filmTelegramBot.setBotToken(botProperties.getBotToken());
        filmTelegramBot.setBotPath(botProperties.getWebHookPath());

        return filmTelegramBot;
    }
}
