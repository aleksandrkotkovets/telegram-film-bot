package com.telegram.film_bot.service.menu;

import com.telegram.film_bot.service.keyboard.MenuKeyboard;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Service
public class MainMenuService extends AbstractMenuService {

    private final MenuKeyboard menuKeyboard;

    public MainMenuService(MenuKeyboard menuKeyboard) {
        this.menuKeyboard = menuKeyboard;
    }

    public SendMessage getMainMenuMessage(final long chatId, final String textMessage) {
        final ReplyKeyboardMarkup replyKeyboardMarkup = menuKeyboard.getMainMenuKeyboard();
        return createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup);
    }

}
