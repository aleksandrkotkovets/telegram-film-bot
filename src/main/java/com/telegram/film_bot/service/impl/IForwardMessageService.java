package com.telegram.film_bot.service.impl;

import org.telegram.telegrambots.meta.api.methods.ForwardMessage;

import java.util.List;

public interface IForwardMessageService {
    List<ForwardMessage> getForwardMessageList(long chatId, List<Integer> messageIdList);

    void forwardMessage(List<ForwardMessage> forwardMessageList);
}
