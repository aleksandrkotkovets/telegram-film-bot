package com.telegram.film_bot.botapi.handler.menu;

import com.telegram.film_bot.botapi.handler.BotState;
import com.telegram.film_bot.botapi.handler.InputMessageHandler;
import com.telegram.film_bot.service.message.ReplyMessagesService;
import com.telegram.film_bot.utils.Emoji;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class HelpMenuHandler implements InputMessageHandler {

    private ReplyMessagesService messagesService;

    public HelpMenuHandler(ReplyMessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {
        return messagesService.getReplyMessage(message.getChatId(),
                "reply.helpMenu.helpMessage",
                Emoji.ONE,
                Emoji.TWO,
                Emoji.INFORMATION_SOURCE,
                Emoji.CINEMA,
                Emoji.WARNING,
                Emoji.CHOCOLATE_BAR,
                Emoji.DOUGHNUT,
                Emoji.COOKIE,
                Emoji.HONEY_POT);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_HELP_MENU;
    }


}