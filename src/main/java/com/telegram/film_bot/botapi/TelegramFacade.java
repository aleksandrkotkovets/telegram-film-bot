package com.telegram.film_bot.botapi;

import com.telegram.film_bot.botapi.handler.BotState;
import com.telegram.film_bot.botapi.handler.BotStateContext;
import com.telegram.film_bot.cache.UserDataCache;
import com.telegram.film_bot.service.menu.MainMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
public class TelegramFacade {

    private final BotStateContext botStateContext;
    private final MainMenuService mainMenuService;
    private final UserDataCache userDataCache;
    private final CallbackQueryFacade callbackQueryFacade;

    public TelegramFacade(BotStateContext botStateContext,
                          MainMenuService mainMenuService,
                          UserDataCache userDataCache,
                          CallbackQueryFacade callbackQueryFacade) {
        this.botStateContext = botStateContext;
        this.mainMenuService = mainMenuService;
        this.userDataCache = userDataCache;
        this.callbackQueryFacade = callbackQueryFacade;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        BotApiMethod<?> replyMessage = null;

        //button
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("New callbackQuery from User: {}, userId: {}, with data: {}",
                    update.getCallbackQuery().getFrom().getLastName(),
                    callbackQuery.getFrom().getId(),
                    update.getCallbackQuery().getData());
            return callbackQueryFacade.processCallbackQuery(update.getCallbackQuery());
        }

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
                    message.getFrom().getUserName(),
                    message.getFrom().getId(),
                    message.getChatId(), message.getText());

            replyMessage = handleInputMessage(message);
        }
        return replyMessage;
    }

    private BotApiMethod<?> handleInputMessage(Message message) {
        String inputMsg = message.getText();
        int userId = message.getFrom().getId();

        BotState botState;
        BotApiMethod<?> replyMessage;

        switch (inputMsg) {
            case "/start":
                botState = BotState.SHOW_MAIN_MENU;
                break;
            case "Поиск":
                botState = BotState.FIND_FILM;
                break;
            case "Рандомный фильм":
                botState = BotState.SHOW_RANDOM_FILM;
                break;
            case "Топ-5 фильмов":
                botState = BotState.SHOW_TOP_FILMS;
                break;
            case "Помощь":
                botState = BotState.SHOW_HELP_MENU;
                break;
            default:
                botState = userDataCache.getUsersCurrentBotState(userId);
                break;
        }

        userDataCache.setUsersCurrentBotState(userId, botState);

        replyMessage = botStateContext.processInputMessage(botState, message);
        return replyMessage;
    }

}