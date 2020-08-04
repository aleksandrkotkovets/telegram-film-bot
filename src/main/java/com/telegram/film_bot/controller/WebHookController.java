package com.telegram.film_bot.controller;

import com.telegram.film_bot.botapi.FilmTelegramBot;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class WebHookController {

    private final FilmTelegramBot filmTelegramBot;

    public WebHookController(FilmTelegramBot filmTelegramBot) {
        this.filmTelegramBot = filmTelegramBot;
    }

    @PostMapping()
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return filmTelegramBot.onWebhookUpdateReceived(update);
    }

}
