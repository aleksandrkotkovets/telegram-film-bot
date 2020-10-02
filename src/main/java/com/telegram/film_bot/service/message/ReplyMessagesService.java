package com.telegram.film_bot.service.message;

import com.telegram.film_bot.utils.Emoji;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Service
public class ReplyMessagesService {

    private LocaleMessageService localeMessageService;

    public ReplyMessagesService(LocaleMessageService messageService) {
        this.localeMessageService = messageService;
    }

    public SendMessage getReplyMessage(long chatId, String replyMessage) {
        return new SendMessage(chatId, localeMessageService.getMessage(replyMessage));
    }

    public SendMessage getReplyMessage(long chatId, String replyMessage, Object... args) {
        return new SendMessage(chatId, localeMessageService.getMessage(replyMessage, args));
    }

    public String getReplyText(String replyText, Object... args) {
        return localeMessageService.getMessage(replyText,args);
    }
    public String getReplyText(String replyText) {
        return localeMessageService.getMessage(replyText);
    }

    public BotApiMethod<?> getWarningReplyMessage(long chatId, String replyMessage) {
        return new SendMessage(chatId, localeMessageService.getMessage(replyMessage));
    }

    public BotApiMethod<?> getWarningReplyMessage(long chatId, String replyMessage, Object... args) {
        return new SendMessage(chatId, localeMessageService.getMessage(replyMessage,args));
    }

    public BotApiMethod<Message> getReplyMessageBySize(long chatId, List<?> list) {
        int size = list.size();
        return size > 0
                ?
                getReplyMessage(chatId, "reply.filmSearch.findedFilms", Emoji.ARROW_UP, size, Emoji.POPCORN)
                :
                getReplyMessage(chatId, "reply.emptyList");
    }
}
