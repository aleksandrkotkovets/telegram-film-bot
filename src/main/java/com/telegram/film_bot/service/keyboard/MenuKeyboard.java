package com.telegram.film_bot.service.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuKeyboard {

    public ReplyKeyboardMarkup getMainMenuKeyboardUser() {

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        KeyboardRow row4 = new KeyboardRow();
        KeyboardRow row5 = new KeyboardRow();

        row1.add(new KeyboardButton("Поиск"));
        row2.add(new KeyboardButton("Рандомный фильм"));
        row3.add(new KeyboardButton("Топ-5 фильмов"));
        row4.add(new KeyboardButton("Посоветовать фильм"));
        row5.add(new KeyboardButton("Помощь"));

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);
        keyboard.add(row5);

        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getMainMenuKeyboardAdmin() {

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        KeyboardRow row4 = new KeyboardRow();
        KeyboardRow row5 = new KeyboardRow();
        KeyboardRow row6 = new KeyboardRow();

        row1.add(new KeyboardButton("Поиск"));
        row2.add(new KeyboardButton("Рандомный фильм"));
        row3.add(new KeyboardButton("Топ-5 фильмов"));
        row4.add(new KeyboardButton("Посоветовать фильм"));
        row5.add(new KeyboardButton("Помощь"));
        row6.add(new KeyboardButton("Показать рекоммендации"));

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);
        keyboard.add(row5);
        keyboard.add(row6);

        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }
}
