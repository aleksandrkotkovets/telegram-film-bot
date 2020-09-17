package com.telegram.film_bot.botapi.handler.find;

import com.telegram.film_bot.botapi.handler.BotState;
import com.telegram.film_bot.botapi.handler.InputMessageHandler;
import com.telegram.film_bot.cache.UserDataCache;
import com.telegram.film_bot.data.entities.Film;
import com.telegram.film_bot.service.ReplyMessagesService;
import com.telegram.film_bot.service.film.impl.FilmService;
import com.telegram.film_bot.service.find.FindFilmMessageService;
import com.telegram.film_bot.service.impl.IChatService;
import com.telegram.film_bot.service.impl.IForwardMessageService;
import com.telegram.film_bot.service.impl.IUserService;
import com.telegram.film_bot.utils.TelegramClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
public class FilmSearchHandler implements InputMessageHandler {

    @Value("${telegram.channel.id}")
    private String channelId;

    private final FindFilmMessageService findFilmMessageService;
    private final FilmService filmService;
    private final UserDataCache userDataCache;
    private final ReplyMessagesService messagesService;
    private final TelegramClient telegramClient;
    private final IChatService chatService;
    private final IUserService userService;
    private final IForwardMessageService forwardMessageService;

    public FilmSearchHandler(FindFilmMessageService findFilmMessageService,
                             FilmService filmService,
                             UserDataCache userDataCache,
                             ReplyMessagesService messagesService,
                             TelegramClient telegramClient,
                             IChatService chatService,
                             IUserService userService, IForwardMessageService forwardMessageService) {
        this.findFilmMessageService = findFilmMessageService;
        this.filmService = filmService;
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
        this.telegramClient = telegramClient;
        this.chatService = chatService;
        this.userService = userService;
        this.forwardMessageService = forwardMessageService;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        if (userDataCache.getUsersCurrentBotState(message.getFrom().getId()).equals(BotState.FIND_FILM)) {
            userDataCache.setUsersCurrentBotState(message.getFrom().getId(), BotState.FIND_FILM_STARTED);
            return findFilmMessageService.getFindFilmMessage(message.getChatId(), "reply.findFilmMenuMessage");
        }
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FIND_FILM;
    }


    private BotApiMethod<?> processUsersInput(Message inputMsg) {
        String usersAnswer = inputMsg.getText();
        int userId = userService.getUserId(inputMsg);
        long chatId = chatService.getChatId(inputMsg);
        BotApiMethod<?> replyToUser = messagesService.getWarningReplyMessage(chatId, "reply.filmSearch.tryAgain");

        BotState botState = userDataCache.getUsersCurrentBotState(userId);

        if (botState.equals(BotState.FIND_FILM_STARTED)) {
            List<Film> films = filmService.findFilmByMessageTextIncludeCriteria(usersAnswer);
            replyToUser = forwardMessageService.getForwardMessageList(chatId, films, channelId, telegramClient, messagesService);
        }

        return replyToUser;
    }


}