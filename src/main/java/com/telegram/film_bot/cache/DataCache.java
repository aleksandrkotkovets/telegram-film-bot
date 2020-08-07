package com.telegram.film_bot.cache;

import com.telegram.film_bot.botapi.state.BotState;


public interface DataCache {
    void setUsersCurrentBotState(int userId, BotState botState);

    BotState getUsersCurrentBotState(int userId);
}
