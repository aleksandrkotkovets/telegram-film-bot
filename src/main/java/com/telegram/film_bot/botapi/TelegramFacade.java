package com.telegram.film_bot.botapi;

import com.telegram.film_bot.botapi.handler.BotState;
import com.telegram.film_bot.botapi.handler.BotStateContext;
import com.telegram.film_bot.cache.UserDataCache;
import com.telegram.film_bot.service.commands.CommandBotStateOrchestratorService;
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
    private final UserDataCache userDataCache;
    private final CallbackQueryFacade callbackQueryFacade;
    private final CommandBotStateOrchestratorService commandBotStateOrchestratorService;

    public TelegramFacade(BotStateContext botStateContext,
                          UserDataCache userDataCache,
                          CallbackQueryFacade callbackQueryFacade,
                          CommandBotStateOrchestratorService commandBotStateOrchestratorService) {
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
        this.callbackQueryFacade = callbackQueryFacade;
        this.commandBotStateOrchestratorService = commandBotStateOrchestratorService;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        BotApiMethod<?> replyMessage = null;

        //button
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("New callbackQuery from User: {}, userId: {}, with data: {}",
                    update.getCallbackQuery().getFrom().getUserName(),
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

        BotState botState = getBotState(inputMsg, userId);
        userDataCache.setUsersCurrentBotState(userId, botState);

        return botStateContext.processInputMessage(botState, message);
    }

    private BotState getBotState(String inputMsg, int userId) {
        BotState processBotState = commandBotStateOrchestratorService.getBotStateByCommand(inputMsg);
        return (processBotState == null) ? userDataCache.getUsersCurrentBotState(userId) : processBotState;
    }

}