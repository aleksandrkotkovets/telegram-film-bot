package com.telegram.film_bot.service.impl;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface IUserService {

    Integer getUserId(Message inputMsg);

    Integer getUserId(CallbackQuery callbackQuery);
}
