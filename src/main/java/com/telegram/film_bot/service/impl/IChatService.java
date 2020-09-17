package com.telegram.film_bot.service.impl;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface IChatService {
    Long getChatId(Message inputMsg);

    Long getChatId(CallbackQuery callbackQuery);
}
