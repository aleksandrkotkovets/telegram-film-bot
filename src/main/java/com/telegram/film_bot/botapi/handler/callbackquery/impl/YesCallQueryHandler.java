package com.telegram.film_bot.botapi.handler.callbackquery.impl;

import com.telegram.film_bot.botapi.handler.BotState;
import com.telegram.film_bot.botapi.handler.callbackquery.CallbackQueryHandler;
import com.telegram.film_bot.botapi.handler.callbackquery.CallbackQueryType;
import com.telegram.film_bot.cache.UserDataCache;
import com.telegram.film_bot.service.ReplyMessagesService;
import com.telegram.film_bot.service.find.FindFilmMessageService;
import com.telegram.film_bot.service.impl.IChatService;
import com.telegram.film_bot.service.impl.IUserService;
import com.telegram.film_bot.service.keyboard.Keyboard;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class YesCallQueryHandler implements CallbackQueryHandler {

    private final UserDataCache userDataCache;
    private final Keyboard keyboard;
    private final FindFilmMessageService findFilmMessageService;
    private final ReplyMessagesService messagesService;
    private final IUserService userService;
    private final IChatService chatService;


    public YesCallQueryHandler(UserDataCache userDataCache,
                               Keyboard keyboard,
                               FindFilmMessageService findFilmMessageService,
                               ReplyMessagesService messagesService,
                               IUserService userService,
                               IChatService chatService) {

        this.userDataCache = userDataCache;
        this.keyboard = keyboard;
        this.findFilmMessageService = findFilmMessageService;
        this.messagesService = messagesService;
        this.userService = userService;
        this.chatService = chatService;
    }

    @Override
    public BotApiMethod<?> handleCallbackQuery(CallbackQuery callbackQuery) {
        return processUsersInput(callbackQuery);
    }

    @Override
    public CallbackQueryType getHandlerQueryType() {
        return CallbackQueryType.YES;
    }

    private BotApiMethod<?> processUsersInput(CallbackQuery callbackQuery) {
        Integer userId = userService.getUserId(callbackQuery);
        Long chatId = chatService.getChatId(callbackQuery);
        BotState usersCurrentBotState = userDataCache.getUsersCurrentBotState(userId);
        BotApiMethod<?> replyToUser = messagesService.getWarningReplyMessage(chatId, "reply.filmSearch.tryAgain");
        switch (usersCurrentBotState) {
            case RANDOM_FILM_ASK: {
                SendMessage sendMessage = findFilmMessageService.getFindFilmMessage(chatId, "reply.randomFilmShowParams");
                userDataCache.setUsersCurrentBotState(userId, BotState.RANDOM_FILM_SHOW_ASK_PARAM_KEYBOARD);
                sendMessage.setReplyMarkup(keyboard.getFilmMainParamsKeyboard());
                replyToUser = sendMessage;
                break;
            }

        }
        return replyToUser;
    }
}
