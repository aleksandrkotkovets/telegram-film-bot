package com.telegram.film_bot.botapi.handler.main;

import com.telegram.film_bot.botapi.handler.BotState;
import com.telegram.film_bot.botapi.handler.InputMessageHandler;
import com.telegram.film_bot.cache.UserDataCache;
import com.telegram.film_bot.data.entities.Film;
import com.telegram.film_bot.service.film.impl.FilmService;
import com.telegram.film_bot.service.impl.IChatService;
import com.telegram.film_bot.service.impl.IForwardMessageService;
import com.telegram.film_bot.service.impl.IUserService;
import com.telegram.film_bot.service.message.ReplyMessagesService;
import com.telegram.film_bot.utils.Emoji;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
public class TopFilmHandler extends AbstractFilmHelper implements InputMessageHandler {

    @Value("${film.top}")
    private Integer countTopFilms;

    private final FilmService filmService;
    private final UserDataCache userDataCache;
    private final ReplyMessagesService messagesService;
    private final IForwardMessageService forwardMessageService;
    private final IChatService chatService;
    private final IUserService userService;

    public TopFilmHandler(FilmService filmService,
                          UserDataCache userDataCache,
                          ReplyMessagesService messagesService,
                          IForwardMessageService forwardMessageService,
                          IChatService chatService,
                          IUserService userService) {
        this.filmService = filmService;
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
        this.forwardMessageService = forwardMessageService;
        this.chatService = chatService;
        this.userService = userService;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_TOP_FILMS;
    }


    private BotApiMethod<?> processUsersInput(Message inputMsg) {
        int userId = userService.getUserId(inputMsg);
        long chatId = chatService.getChatId(inputMsg);
        BotApiMethod<?> replyToUser = messagesService.getWarningReplyMessage(chatId, "reply.filmSearch.tryAgain", Emoji.INFORMATION_SOURCE, Emoji.INFORMATION_SOURCE);

        BotState botState = userDataCache.getUsersCurrentBotState(userId);

        if (botState.equals(BotState.SHOW_TOP_FILMS)) {
//            userDataCache.setUsersCurrentBotState(userId, BotState.GET_TOP_FILMS);
            List<Film> films = filmService.findTopFilmByCount(countTopFilms);
            List<Integer> idList = getIdList(films);
            List<ForwardMessage> forwardMessageList = forwardMessageService.getForwardMessageList(chatId, idList);
            forwardMessageService.forwardMessage(forwardMessageList);
            replyToUser = messagesService.getReplyMessageBySize(chatId, forwardMessageList);
        }

        return replyToUser;
    }


}