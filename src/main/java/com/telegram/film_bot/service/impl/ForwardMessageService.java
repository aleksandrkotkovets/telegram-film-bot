package com.telegram.film_bot.service.impl;

import com.telegram.film_bot.FilmTelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ForwardMessageService implements IForwardMessageService {

    @Value("${telegram.channel.id}")
    private String channelId;

    private final FilmTelegramBot filmTelegramBot;

    public ForwardMessageService(@Lazy FilmTelegramBot filmTelegramBot) {
        this.filmTelegramBot = filmTelegramBot;
    }

    @Override
    public List<ForwardMessage> getForwardMessageList(long chatId, List<Integer> messageIdList) {
        return messageIdList.stream().map(messageId -> new ForwardMessage(chatId, channelId, messageId)).collect(Collectors.toList());
    }

    /**
     * Telegram methods
     */

    @Override
    public void forwardMessage(List<ForwardMessage> forwardMessageList) {
        filmTelegramBot.forwardMessage(forwardMessageList);
    }
}
