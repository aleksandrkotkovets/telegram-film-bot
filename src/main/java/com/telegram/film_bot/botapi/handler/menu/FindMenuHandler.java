package com.telegram.film_bot.botapi.handler.menu;

import com.telegram.film_bot.botapi.InputMessageHandler;
import com.telegram.film_bot.botapi.state.BotState;
import com.telegram.film_bot.service.ReplyMessagesService;
import com.telegram.film_bot.service.menu.FindMenuService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class FindMenuHandler implements InputMessageHandler {

    private ReplyMessagesService messagesService;
    private FindMenuService findMenuService;

    public FindMenuHandler(ReplyMessagesService messagesService, FindMenuService findMenuService) {
        this.messagesService = messagesService;
        this.findMenuService = findMenuService;
    }

    @Override
    public SendMessage handle(Message message) {
        return findMenuService.getFindMenuMessage(message.getChatId(), messagesService.getReplyText("reply.findMenu.chooseFindTypeMessage"));
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_FIND_MENU;
    }


}
