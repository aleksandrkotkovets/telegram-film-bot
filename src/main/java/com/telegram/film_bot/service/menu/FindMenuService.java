package com.telegram.film_bot.service.menu;

import com.telegram.film_bot.service.keyboard.MenuKeyboard;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Service
public class FindMenuService extends AbstractMenuService {

    private final MenuKeyboard menuKeyboard;

    public FindMenuService(MenuKeyboard menuKeyboard) {
        this.menuKeyboard = menuKeyboard;
    }

    public SendMessage getFindMenuMessage(final long chatId, final String textMessage) {
        InlineKeyboardMarkup inlineKeyboardMarkup = menuKeyboard.getFindMenuInlineKeyboard();
        final SendMessage findMenuMessage = createMessageWithKeyboard(chatId, textMessage, inlineKeyboardMarkup);

        return findMenuMessage;
    }

}
