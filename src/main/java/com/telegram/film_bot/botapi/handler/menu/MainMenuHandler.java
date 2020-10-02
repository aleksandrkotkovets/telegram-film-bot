package com.telegram.film_bot.botapi.handler.menu;

import com.telegram.film_bot.botapi.handler.BotState;
import com.telegram.film_bot.botapi.handler.InputMessageHandler;
import com.telegram.film_bot.service.menu.MainMenuService;
import com.telegram.film_bot.service.message.ReplyMessagesService;
import com.telegram.film_bot.utils.Emoji;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MainMenuHandler implements InputMessageHandler {

    private ReplyMessagesService messagesService;
    private MainMenuService mainMenuService;

    public MainMenuHandler(ReplyMessagesService messagesService,
                           MainMenuService mainMenuService) {
        this.messagesService = messagesService;
        this.mainMenuService = mainMenuService;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {

        SendMessage mainMenuMessage = mainMenuService.getMainMenuMessage(message, messagesService.getReplyText("reply.mainMenu.welcomeMessage", Emoji.EXCLAMATION, Emoji.EXCLAMATION));

        String helpMessage = messagesService.getReplyText("reply.helpMessage");
        String replyMessage = mainMenuMessage.getText() + "\n\n" + helpMessage;

        mainMenuMessage.setText(replyMessage);
        return mainMenuMessage;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_MAIN_MENU;
    }


}
