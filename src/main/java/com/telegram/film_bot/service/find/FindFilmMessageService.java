package com.telegram.film_bot.service.find;

import com.telegram.film_bot.data.entities.Film;
import com.telegram.film_bot.service.message.ReplyMessagesService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class FindFilmMessageService {

    private final ReplyMessagesService messagesService;

    public FindFilmMessageService(ReplyMessagesService messagesService) {
        this.messagesService = messagesService;
    }

    public SendMessage getFindFilmMessage(final long chatId, final String textMessage, Object... args) {
        return messagesService.getReplyMessage(chatId, textMessage,args);
    }

    public SendMessage getFindFilmMessage(final long chatId, final String textMessage) {
        return messagesService.getReplyMessage(chatId, textMessage);
    }

    public BotApiMethod<?> getForwardMessage(Long chatId, Film film) {
        return new ForwardMessage(chatId, film.getChannelId(), Math.toIntExact(film.getMessageId()));
    }

}
