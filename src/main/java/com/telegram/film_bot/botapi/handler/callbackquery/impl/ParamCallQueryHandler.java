package com.telegram.film_bot.botapi.handler.callbackquery.impl;

import com.telegram.film_bot.botapi.handler.BotState;
import com.telegram.film_bot.cache.UserDataCache;
import com.telegram.film_bot.data.entities.Film;
import com.telegram.film_bot.service.message.ReplyMessagesService;
import com.telegram.film_bot.service.film.impl.FilmService;
import com.telegram.film_bot.service.find.FindFilmMessageService;
import com.telegram.film_bot.service.impl.IChatService;
import com.telegram.film_bot.service.impl.IUserService;
import com.telegram.film_bot.utils.Emoji;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class ParamCallQueryHandler {

    private final UserDataCache userDataCache;
    private final FindFilmMessageService findFilmMessageService;
    private final ReplyMessagesService messagesService;
    private final FilmService filmService;
    private final IUserService userService;
    private final IChatService chatService;

    public ParamCallQueryHandler(UserDataCache userDataCache,
                                 FindFilmMessageService findFilmMessageService,
                                 ReplyMessagesService messagesService,
                                 FilmService filmService,
                                 IUserService userService,
                                 IChatService chatService) {

        this.userDataCache = userDataCache;
        this.findFilmMessageService = findFilmMessageService;
        this.messagesService = messagesService;
        this.filmService = filmService;
        this.userService = userService;
        this.chatService = chatService;
    }

    public BotApiMethod<?> handleCallbackQueryByParam(CallbackQuery callbackQuery, String queryType) {
        Integer userId = userService.getUserId(callbackQuery);
        Long chatId = chatService.getChatId(callbackQuery);
        BotState usersCurrentBotState = userDataCache.getUsersCurrentBotState(userId);
        BotApiMethod<?> replyToUser = messagesService.getWarningReplyMessage(chatId, "reply.query.failed",Emoji.INFORMATION_SOURCE);
        switch (usersCurrentBotState) {
            case RANDOM_FILM_CHOOSE_GENRE:
            case RANDOM_FILM_CHOOSE_RATE:
            case RANDOM_FILM_CHOOSE_YEAR: {
                userDataCache.setUsersCurrentBotState(userId, BotState.GET_RANDOM_FILM_BY_PARAM);
                Film film = filmService.getRandomFilmByParam(queryType);
                replyToUser = findFilmMessageService.getForwardMessage(chatId, film);
                break;
            }
        }
        return replyToUser;
    }

}
