package com.telegram.film_bot.botapi.handler.main.recommendations;

import com.telegram.film_bot.botapi.handler.BotState;
import com.telegram.film_bot.botapi.handler.InputMessageHandler;
import com.telegram.film_bot.botapi.handler.main.AbstractFilmHelper;
import com.telegram.film_bot.cache.UserDataCache;
import com.telegram.film_bot.data.entities.RecommendFilm;
import com.telegram.film_bot.service.film.impl.FilmService;
import com.telegram.film_bot.service.impl.IChatService;
import com.telegram.film_bot.service.impl.IUserService;
import com.telegram.film_bot.service.message.ReplyMessagesService;
import com.telegram.film_bot.utils.Emoji;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

import static com.telegram.film_bot.utils.StringHelper.createMessage;

@Component
public class UnreadRecommendationsHandler extends AbstractFilmHelper implements InputMessageHandler {

    private final UserDataCache userDataCache;
    private final ReplyMessagesService messagesService;
    private final IChatService chatService;
    private final IUserService userService;
    private final FilmService filmService;

    public UnreadRecommendationsHandler(UserDataCache userDataCache,
                                        ReplyMessagesService messagesService,
                                        IChatService chatService,
                                        IUserService userService, FilmService filmService) {
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
        this.chatService = chatService;
        this.userService = userService;
        this.filmService = filmService;
    }


    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_RECOMMENDATIONS_UNREAD;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        return processUsersInput(message);
    }

    private BotApiMethod<?> processUsersInput(Message inputMsg) {
        int userId = userService.getUserId(inputMsg);
        long chatId = chatService.getChatId(inputMsg);

        SendMessage replyToUser = (SendMessage) messagesService.getWarningReplyMessage(chatId, "reply.filmSearch.tryAgain", Emoji.INFORMATION_SOURCE);

        BotState botState = userDataCache.getUsersCurrentBotState(userId);

        switch (botState) {
            case SHOW_RECOMMENDATIONS_UNREAD:
                userDataCache.setUsersCurrentBotState(userId, BotState.GET_RECOMMENDATIONS_UNREAD);
                replyToUser = messagesService.getReplyMessage(chatId, "reply.showRecommendations");
                List<RecommendFilm> unReadRecommendations = filmService.getUnReadRecommendations();
                List<String> messageList = getMessageList(unReadRecommendations);
                String message = createMessage(messageList);
                replyToUser.setText(replyToUser.getText() + "\n\n" + message);
                break;
        }

        return replyToUser;
    }

}
