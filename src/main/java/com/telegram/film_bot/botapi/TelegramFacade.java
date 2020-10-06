package com.telegram.film_bot.botapi;

import com.telegram.film_bot.botapi.handler.BotState;
import com.telegram.film_bot.botapi.handler.BotStateContext;
import com.telegram.film_bot.cache.UserDataCache;
import com.telegram.film_bot.service.commands.CommandBotStateOrchestratorService;
import com.telegram.film_bot.service.message.ReplyMessagesService;
import com.telegram.film_bot.utils.Emoji;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
    private final ReplyMessagesService messagesService;

    public TelegramFacade(BotStateContext botStateContext,
                          UserDataCache userDataCache,
                          CallbackQueryFacade callbackQueryFacade,
                          CommandBotStateOrchestratorService commandBotStateOrchestratorService,
                          ReplyMessagesService messagesService) {
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
        this.callbackQueryFacade = callbackQueryFacade;
        this.commandBotStateOrchestratorService = commandBotStateOrchestratorService;
        this.messagesService = messagesService;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        BotApiMethod<?> replyMessage = null;

        //button
        try {
            if (update.hasCallbackQuery()) {
                CallbackQuery callbackQuery = update.getCallbackQuery();
                log.info("New callbackQuery from User: {}, userId: {}, with data: {}",
                        update.getCallbackQuery().getFrom().getUserName(),
                        callbackQuery.getFrom().getId(),
                        update.getCallbackQuery().getData());
                return callbackQueryFacade.processCallbackQuery(update.getCallbackQuery());
            }
        } catch (NullPointerException e) {
            replyMessage = new SendMessage(update.getCallbackQuery().getMessage().getChatId(), messagesService.getReplyText("reply.filmSearch.tryAgain", Emoji.INFORMATION_SOURCE));
        }

        try {
            Message message = update.getMessage();
            if (message != null && message.hasText()) {
                log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
                        message.getFrom().getUserName(),
                        message.getFrom().getId(),
                        message.getChatId(), message.getText());

                replyMessage = handleInputMessage(message);
            }
        } catch (NullPointerException e) {
            replyMessage = new SendMessage(update.getMessage().getChatId(), messagesService.getReplyText("reply.filmSearch.tryAgain", Emoji.INFORMATION_SOURCE));
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