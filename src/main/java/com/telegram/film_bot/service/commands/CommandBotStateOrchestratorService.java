package com.telegram.film_bot.service.commands;

import com.telegram.film_bot.botapi.command.Commands;
import com.telegram.film_bot.botapi.handler.BotState;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandBotStateOrchestratorService {
    private final Map<String, BotState> botStates;

    public CommandBotStateOrchestratorService() {
        this.botStates = new HashMap<String, BotState>() {{
            put(Commands.START, BotState.SHOW_MAIN_MENU);
            put(Commands.START1, BotState.SHOW_MAIN_MENU);
            put(Commands.START2, BotState.SHOW_MAIN_MENU);
            put(Commands.START3, BotState.SHOW_MAIN_MENU);
            put(Commands.START4, BotState.SHOW_MAIN_MENU);
            put(Commands.START5, BotState.SHOW_MAIN_MENU);

            put(Commands.FIND, BotState.FIND_FILM);
            put(Commands.FIND1, BotState.FIND_FILM);
            put(Commands.FIND2, BotState.FIND_FILM);
            put(Commands.FIND3, BotState.FIND_FILM);
            put(Commands.FIND4, BotState.FIND_FILM);
            put(Commands.FIND5, BotState.FIND_FILM);

            put(Commands.RANDOM, BotState.SHOW_RANDOM_FILM);
            put(Commands.RANDOM1, BotState.SHOW_RANDOM_FILM);
            put(Commands.RANDOM2, BotState.SHOW_RANDOM_FILM);
            put(Commands.RANDOM3, BotState.SHOW_RANDOM_FILM);
            put(Commands.RANDOM4, BotState.SHOW_RANDOM_FILM);
            put(Commands.RANDOM5, BotState.SHOW_RANDOM_FILM);
            put(Commands.RANDOM6, BotState.SHOW_RANDOM_FILM);
            put(Commands.RANDOM7, BotState.SHOW_RANDOM_FILM);
            put(Commands.RANDOM8, BotState.SHOW_RANDOM_FILM);
            put(Commands.RANDOM9, BotState.SHOW_RANDOM_FILM);
            put(Commands.RANDOM10, BotState.SHOW_RANDOM_FILM);
            put(Commands.RANDOM11, BotState.SHOW_RANDOM_FILM);
            put(Commands.RANDOM12, BotState.SHOW_RANDOM_FILM);

            put(Commands.TOP, BotState.SHOW_TOP_FILMS);
            put(Commands.TOP1, BotState.SHOW_TOP_FILMS);
            put(Commands.TOP2, BotState.SHOW_TOP_FILMS);
            put(Commands.TOP3, BotState.SHOW_TOP_FILMS);
            put(Commands.TOP4, BotState.SHOW_TOP_FILMS);
            put(Commands.TOP5, BotState.SHOW_TOP_FILMS);
            put(Commands.TOP6, BotState.SHOW_TOP_FILMS);

            put(Commands.HELP, BotState.SHOW_HELP_MENU);
            put(Commands.HELP1, BotState.SHOW_HELP_MENU);
            put(Commands.HELP2, BotState.SHOW_HELP_MENU);
            put(Commands.HELP3, BotState.SHOW_HELP_MENU);

            put(Commands.RECOMMEND, BotState.RECOMMEND_FILM);
            put(Commands.RECOMMEND1, BotState.RECOMMEND_FILM);
            put(Commands.RECOMMEND2, BotState.RECOMMEND_FILM);
            put(Commands.RECOMMEND3, BotState.RECOMMEND_FILM);
            put(Commands.RECOMMEND4, BotState.RECOMMEND_FILM);
            put(Commands.RECOMMEND5, BotState.RECOMMEND_FILM);

            put(Commands.RECOMMENDATIONS_UNREAD, BotState.SHOW_RECOMMENDATIONS_UNREAD);
            put(Commands.RECOMMENDATIONS_UNREAD1, BotState.SHOW_RECOMMENDATIONS_UNREAD);
            put(Commands.RECOMMENDATIONS_UNREAD2, BotState.SHOW_RECOMMENDATIONS_UNREAD);
            put(Commands.RECOMMENDATIONS_UNREAD3, BotState.SHOW_RECOMMENDATIONS_UNREAD);
            put(Commands.RECOMMENDATIONS_UNREAD4, BotState.SHOW_RECOMMENDATIONS_UNREAD);

            put(Commands.RECOMMENDATIONS, BotState.SHOW_RECOMMENDATIONS_ALL);
            put(Commands.RECOMMENDATIONS1, BotState.SHOW_RECOMMENDATIONS_ALL);
            put(Commands.RECOMMENDATIONS2, BotState.SHOW_RECOMMENDATIONS_ALL);
            put(Commands.RECOMMENDATIONS3, BotState.SHOW_RECOMMENDATIONS_ALL);
            put(Commands.RECOMMENDATIONS4, BotState.SHOW_RECOMMENDATIONS_ALL);
            put(Commands.RECOMMENDATIONS5, BotState.SHOW_RECOMMENDATIONS_ALL);
        }};

    }

    public BotState getBotStateByCommand(String command) {
        return !command.isEmpty() ? botStates.get(command) : null;
    }

}
