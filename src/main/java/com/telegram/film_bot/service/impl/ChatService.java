package com.telegram.film_bot.service.impl;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class ChatService implements IChatService {

    @Override
    public Long getChatId(Message inputMsg) {
        return inputMsg.getChatId();
    }

    @Override
    public Long getChatId(CallbackQuery callbackQuery) {
        return callbackQuery.getMessage().getChatId();
    }
}
