package com.telegram.film_bot.cache;

import com.telegram.film_bot.botapi.handler.BotState;

import java.util.List;


public interface DataCache {
    void setUsersCurrentBotState(Integer userId, BotState botState);

    BotState getUsersCurrentBotState(Integer userId);

    List<Integer> getAdminList();
}
