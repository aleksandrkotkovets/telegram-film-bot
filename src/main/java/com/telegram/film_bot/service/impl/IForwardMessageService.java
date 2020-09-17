package com.telegram.film_bot.service.impl;

import com.telegram.film_bot.data.entities.Film;
import com.telegram.film_bot.service.ReplyMessagesService;
import com.telegram.film_bot.utils.TelegramClient;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.util.List;

public interface IForwardMessageService {
    BotApiMethod<?> getForwardMessageList(long chatId, List<Film> films, String channelId, TelegramClient telegramClient, ReplyMessagesService messagesService);
}
