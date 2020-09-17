package com.telegram.film_bot.botapi.handler.find;

import com.telegram.film_bot.botapi.handler.BotState;
import com.telegram.film_bot.botapi.handler.InputMessageHandler;
import com.telegram.film_bot.cache.UserDataCache;
import com.telegram.film_bot.service.ReplyMessagesService;
import com.telegram.film_bot.service.find.FindFilmMessageService;
import com.telegram.film_bot.service.impl.IChatService;
import com.telegram.film_bot.service.impl.IUserService;
import com.telegram.film_bot.service.keyboard.Keyboard;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class RandomFilmHandler implements InputMessageHandler {

    private final FindFilmMessageService findFilmMessageService;
    private final UserDataCache userDataCache;
    private final ReplyMessagesService messagesService;
    private final Keyboard keyboard;
    private final IChatService chatService;
    private final IUserService userService;

    public RandomFilmHandler(FindFilmMessageService findFilmMessageService,
                             UserDataCache userDataCache,
                             ReplyMessagesService messagesService,
                             Keyboard keyboard,
                             IChatService chatService,
                             IUserService userService) {
        this.findFilmMessageService = findFilmMessageService;
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
        this.keyboard = keyboard;
        this.chatService = chatService;
        this.userService = userService;
    }


    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_RANDOM_FILM;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {

        return processUsersInput(message);
    }

    private BotApiMethod<?> processUsersInput(Message inputMsg) {
        int userId = userService.getUserId(inputMsg);
        long chatId = chatService.getChatId(inputMsg);

        BotApiMethod<?> replyToUser = messagesService.getWarningReplyMessage(chatId, "reply.filmSearch.tryAgain");

        BotState botState = userDataCache.getUsersCurrentBotState(userId);

        if (botState.equals(BotState.SHOW_RANDOM_FILM)) {
            userDataCache.setUsersCurrentBotState(userId, BotState.RANDOM_FILM_ASK);

            SendMessage sendMessage = findFilmMessageService.getFindFilmMessage(chatId, "reply.randomFilmAsk");
            sendMessage.setReplyMarkup(keyboard.getYesNoKeyboard());
            replyToUser = sendMessage;
        }

        return replyToUser;
    }
}
