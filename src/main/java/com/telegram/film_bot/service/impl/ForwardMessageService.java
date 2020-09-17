package com.telegram.film_bot.service.impl;

import com.telegram.film_bot.data.entities.Film;
import com.telegram.film_bot.service.ReplyMessagesService;
import com.telegram.film_bot.utils.TelegramClient;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForwardMessageService implements IForwardMessageService {

    @Override
    public BotApiMethod<?> getForwardMessageList(long chatId, List<Film> films, String channelId, TelegramClient telegramClient, ReplyMessagesService messagesService) {
        BotApiMethod<?> replyToUser;
        List<ForwardMessage> forwardMessageList = films.stream().map(film -> new ForwardMessage(chatId, channelId, Math.toIntExact(film.getMessageId()))).collect(Collectors.toList());
        forwardMessageList.forEach(film -> telegramClient.forwardFromChannelToUser(chatId, channelId, film.getMessageId()));
        replyToUser = messagesService.getReplyMessage(chatId, "reply.filmSearch.findedFilms", forwardMessageList.size());
        return replyToUser;
    }
}
