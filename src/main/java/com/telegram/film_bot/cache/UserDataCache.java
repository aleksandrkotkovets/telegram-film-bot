package com.telegram.film_bot.cache;

import com.telegram.film_bot.botapi.handler.BotState;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDataCache implements DataCache {

    @Value("${user.admin.id}")
    List<Integer> adminList;

    private Map<Integer, BotState> usersBotStates = new HashMap<>();

    @Override
    public void setUsersCurrentBotState(Integer userId, BotState botState) {
        usersBotStates.put(userId, botState);
    }

    @Override
    public BotState getUsersCurrentBotState(Integer userId) {
        BotState botState = usersBotStates.get(userId);
        if (botState == null) {
            botState = BotState.SHOW_MAIN_MENU;
        }
        return botState;
    }

    @Override
    public List<Integer> getAdminList() {
        return this.adminList;
    }

}
