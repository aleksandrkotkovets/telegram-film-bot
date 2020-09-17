package com.telegram.film_bot.service.impl;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class UserService implements IUserService {

    @Override
    public Integer getUserId(Message inputMsg) {
        return inputMsg.getFrom().getId();
    }

    @Override
    public Integer getUserId(CallbackQuery callbackQuery) {
        return callbackQuery.getFrom().getId();
    }
}
