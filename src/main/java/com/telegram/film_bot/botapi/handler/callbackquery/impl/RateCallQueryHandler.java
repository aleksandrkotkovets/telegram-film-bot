package com.telegram.film_bot.botapi.handler.callbackquery.impl;

import com.telegram.film_bot.botapi.handler.BotState;
import com.telegram.film_bot.botapi.handler.callbackquery.CallbackQueryHandler;
import com.telegram.film_bot.botapi.handler.callbackquery.CallbackQueryType;
import com.telegram.film_bot.cache.UserDataCache;
import com.telegram.film_bot.service.message.ReplyMessagesService;
import com.telegram.film_bot.service.film.impl.FilmService;
import com.telegram.film_bot.service.find.FindFilmMessageService;
import com.telegram.film_bot.service.impl.IChatService;
import com.telegram.film_bot.service.impl.IUserService;
import com.telegram.film_bot.service.keyboard.Keyboard;
import com.telegram.film_bot.utils.Emoji;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;

@Component
public class RateCallQueryHandler implements CallbackQueryHandler {

    private final UserDataCache userDataCache;
    private final Keyboard keyboard;
    private final FindFilmMessageService findFilmMessageService;
    private final ReplyMessagesService messagesService;
    private final FilmService filmService;
    private final IUserService userService;
    private final IChatService chatService;

    public RateCallQueryHandler(UserDataCache userDataCache,
                                Keyboard keyboard,
                                FindFilmMessageService findFilmMessageService,
                                ReplyMessagesService messagesService,
                                FilmService filmService,
                                IUserService userService,
                                IChatService chatService) {

        this.userDataCache = userDataCache;
        this.keyboard = keyboard;
        this.findFilmMessageService = findFilmMessageService;
        this.messagesService = messagesService;
        this.filmService = filmService;
        this.userService = userService;
        this.chatService = chatService;
    }

    @Override
    public BotApiMethod<?> handleCallbackQuery(CallbackQuery callbackQuery) {
        return processUsersInput(callbackQuery);
    }

    @Override
    public CallbackQueryType getHandlerQueryType() {
        return CallbackQueryType.RATE;
    }

    private BotApiMethod<?> processUsersInput(CallbackQuery callbackQuery) {
        Integer userId = userService.getUserId(callbackQuery);
        Long chatId = chatService.getChatId(callbackQuery);
        BotState usersCurrentBotState = userDataCache.getUsersCurrentBotState(userId);
        BotApiMethod<?> replyToUser = messagesService.getWarningReplyMessage(chatId, "reply.query.failed",Emoji.INFORMATION_SOURCE);

        switch (usersCurrentBotState) {
            case RANDOM_FILM_SHOW_ASK_PARAM_KEYBOARD: {
                userDataCache.setUsersCurrentBotState(userId, BotState.RANDOM_FILM_CHOOSE_RATE);
                SendMessage sendMessage = findFilmMessageService.getFindFilmMessage(chatId, "reply.chooseRate", Emoji.ARROW_DOWN,Emoji.ARROW_DOWN);
                List<String> rateList = filmService.getUniqParamList(CallbackQueryType.RATE);
                sendMessage.setReplyMarkup(keyboard.getParamsKeyboard(rateList));
                replyToUser = sendMessage;
                break;
            }

        }
        return replyToUser;
    }
}
