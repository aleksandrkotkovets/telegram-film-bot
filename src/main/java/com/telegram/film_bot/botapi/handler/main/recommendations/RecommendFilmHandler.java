package com.telegram.film_bot.botapi.handler.main.recommendations;

import com.telegram.film_bot.botapi.handler.BotState;
import com.telegram.film_bot.botapi.handler.InputMessageHandler;
import com.telegram.film_bot.cache.UserDataCache;
import com.telegram.film_bot.service.film.impl.RecommendFilmService;
import com.telegram.film_bot.service.impl.IChatService;
import com.telegram.film_bot.service.impl.IUserService;
import com.telegram.film_bot.service.message.ReplyMessagesService;
import com.telegram.film_bot.utils.Emoji;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class RecommendFilmHandler implements InputMessageHandler {

    private final UserDataCache userDataCache;
    private final ReplyMessagesService messagesService;
    private final IChatService chatService;
    private final IUserService userService;
    private final RecommendFilmService recommendFilmService;

    public RecommendFilmHandler(UserDataCache userDataCache,
                                ReplyMessagesService messagesService,
                                IChatService chatService,
                                IUserService userService,
                                RecommendFilmService recommendFilmService) {
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
        this.chatService = chatService;
        this.userService = userService;
        this.recommendFilmService = recommendFilmService;
    }


    @Override
    public BotState getHandlerName() {
        return BotState.RECOMMEND_FILM;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        return processUsersInput(message);
    }

    private synchronized BotApiMethod<?> processUsersInput(Message inputMsg) {
        int userId = userService.getUserId(inputMsg);
        long chatId = chatService.getChatId(inputMsg);

        BotApiMethod<?> replyToUser = messagesService.getWarningReplyMessage(chatId, "reply.filmSearch.tryAgain", Emoji.INFORMATION_SOURCE);

        BotState botState = userDataCache.getUsersCurrentBotState(userId);

        switch (botState) {
            case RECOMMEND_FILM:
                userDataCache.setUsersCurrentBotState(userId, BotState.ASK_RECOMMEND_FILM);
                replyToUser = messagesService.getReplyMessage(chatId, "reply.recommendFilmAsk");
                break;
            case ASK_RECOMMEND_FILM:
                userDataCache.setUsersCurrentBotState(userId, BotState.ADD_RECOMMEND_FILM);
                recommendFilmService.saveRecommendedFilm(inputMsg);
                replyToUser = getReplyMessageByParam(inputMsg);
                break;
        }

        return replyToUser;
    }

    public BotApiMethod<?> getReplyMessageByParam(Message message) {
        SendMessage sendMessage;
        long chatId = message.getChatId();
        String userName = message.getFrom().getUserName();
        String userFirstName = message.getFrom().getFirstName();
        String userLastName = message.getFrom().getLastName();
        if (!userFirstName.isEmpty() && !userLastName.isEmpty()) {
            sendMessage = messagesService.getReplyMessage(chatId, "reply.recommendFilmAddExistParam", userFirstName + " " + userLastName);
        } else if (!userName.isEmpty()) {
            sendMessage = messagesService.getReplyMessage(chatId, "reply.recommendFilmAddExistParam", userName);
        } else {
            sendMessage = messagesService.getReplyMessage(chatId, "reply.recommendFilmAdd", Emoji.RELAXED);
        }
        return sendMessage;
    }
}
