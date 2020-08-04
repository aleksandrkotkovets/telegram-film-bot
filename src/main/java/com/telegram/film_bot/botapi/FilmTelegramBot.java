package com.telegram.film_bot.botapi;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilmTelegramBot extends TelegramWebhookBot {

    String botPath;
    String botUsername;
    String botToken;

    private TelegramFacade telegramFacade;

    public FilmTelegramBot(TelegramFacade telegramFacade) {
        this.telegramFacade = telegramFacade;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        SendMessage replyMessageToUser = telegramFacade.handleUpdate(update);
        return replyMessageToUser;
    }


}
