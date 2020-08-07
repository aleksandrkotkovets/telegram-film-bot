package com.telegram.film_bot.botapi;

import com.telegram.film_bot.botapi.state.BotState;
import com.telegram.film_bot.botapi.state.BotStateContext;
import com.telegram.film_bot.cache.UserDataCache;
import com.telegram.film_bot.service.menu.MainMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
public class TelegramFacade {

    private final BotStateContext botStateContext;
    private final MainMenuService mainMenuService;
    private final UserDataCache userDataCache;

    public TelegramFacade(BotStateContext botStateContext,
                          MainMenuService mainMenuService,
                          UserDataCache userDataCache) {
        this.botStateContext = botStateContext;
        this.mainMenuService = mainMenuService;
        this.userDataCache = userDataCache;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        SendMessage replyMessage = null;

        //button
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("New callbackQuery from User: {}, userId: {}, with data: {}",
                    update.getCallbackQuery().getFrom().getUserName(),
                    callbackQuery.getFrom().getId(),
                    update.getCallbackQuery().getData());

            return processCallbackQuery(callbackQuery);
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

    private SendMessage handleInputMessage(Message message) {
        String inputMsg = message.getText();
        int userId = message.getFrom().getId();

        BotState botState;
        SendMessage replyMessage;

        switch (inputMsg) {
            case "/start":
                botState = BotState.SHOW_MAIN_MENU;
                break;
            case "Поиск":
                botState = BotState.SHOW_FIND_MENU;
                break;
            case "Рандомный фильм":
                botState = BotState.SHOW_RANDOM_FILM;
                break;
            case "Топ-5 фильмов":
                botState = BotState.SHOW_TOP_5_FILMS;
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


    private BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        final long chatId = buttonQuery.getMessage().getChatId();
        final int userId = buttonQuery.getFrom().getId();
        BotApiMethod<?> callBackAnswer = mainMenuService.getMainMenuMessage(chatId, "Воспользуйтесь главным меню");

        //From Destiny choose buttons
        if (buttonQuery.getData().equals("CallName")) {
            callBackAnswer = new SendMessage(chatId, "Введите название фильма");
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_FIND_MENU);
        }else if (buttonQuery.getData().equals("CallYear")) {
            callBackAnswer = new SendMessage(chatId, "Введите год выпуска фильма");
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_FIND_MENU);
        }else if (buttonQuery.getData().equals("CallType")) {
            callBackAnswer = new SendMessage(chatId, "Введите жанр фильма");
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_FIND_MENU);
        }else if (buttonQuery.getData().equals("CallRate")) {
            callBackAnswer = new SendMessage(chatId, "Введите рейтинг фильма");
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_FIND_MENU);
        }else if (buttonQuery.getData().equals("CallAllSearchTypes")) {
            callBackAnswer = new SendMessage(chatId, "Выберите параметры для поиска из списка выше");
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_FIND_MENU);
        }else {
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_FIND_MENU);
        }

        return callBackAnswer;
    }

    private AnswerCallbackQuery sendAnswerCallbackQuery(String text, boolean alert, CallbackQuery callbackquery) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackquery.getId());
        answerCallbackQuery.setShowAlert(alert);
        answerCallbackQuery.setText(text);
        return answerCallbackQuery;
    }

}