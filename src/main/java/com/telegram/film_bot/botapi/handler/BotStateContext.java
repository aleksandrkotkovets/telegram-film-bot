package com.telegram.film_bot.botapi.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {

    private Map<BotState, InputMessageHandler> messageHandlers = new HashMap<>();

    public BotStateContext(List<InputMessageHandler> messageHandlers) {
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
    }

    public BotApiMethod<?> processInputMessage(BotState currentState, Message message) {
        InputMessageHandler currentMessageHandler = findMessageHandler(currentState);
        return currentMessageHandler.handle(message);
    }

    private InputMessageHandler findMessageHandler(BotState currentState) {

        if (isShowRecommendationsState(currentState)) {
            return messageHandlers.get(BotState.SHOW_RECOMMENDATIONS_ALL);
        }

        if (isFilmSearchState(currentState)) {
            return messageHandlers.get(BotState.FIND_FILM);
        }

        if (isFilmRandomState(currentState)) {
            return messageHandlers.get(BotState.SHOW_RANDOM_FILM);
        }

        if (isRecommendFilmState(currentState)) {
            return messageHandlers.get(BotState.RECOMMEND_FILM);
        }

        return messageHandlers.get(currentState);
    }

    private boolean isRecommendFilmState(BotState currentState) {
        switch (currentState) {
            case RECOMMEND_FILM:
            case ASK_RECOMMEND_FILM:
            case ADD_RECOMMEND_FILM:
                return true;
            default:
                return false;
        }
    }

    private boolean isFilmSearchState(BotState currentState) {
        switch (currentState) {
            case FIND_FILM:
            case FIND_FILM_STARTED:
                return true;
            default:
                return false;
        }
    }

    private boolean isFilmRandomState(BotState currentState) {
        switch (currentState) {
            case SHOW_RANDOM_FILM:
            case RANDOM_FILM_ASK:
                return true;
            default:
                return false;
        }
    }

    private boolean isShowRecommendationsState(BotState currentState) {
        switch (currentState) {
            case SHOW_RECOMMENDATIONS_ALL:
            case GET_RECOMMENDATIONS_ALL:
                return true;
            default:
                return false;
        }
    }
}
