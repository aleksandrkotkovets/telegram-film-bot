package com.telegram.film_bot.service.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuKeyboard {

    public ReplyKeyboardMarkup getMainMenuKeyboard() {

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        KeyboardRow row4 = new KeyboardRow();

        row1.add(new KeyboardButton("Поиск"));
        row2.add(new KeyboardButton("Рандомный фильм"));
        row3.add(new KeyboardButton("Топ-5 фильмов"));
        row4.add(new KeyboardButton("Помощь"));

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);

        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    public InlineKeyboardMarkup getFindMenuInlineKeyboard() {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton findByType = new InlineKeyboardButton().setText("Жанр").setCallbackData("CallType");
        InlineKeyboardButton findByYear = new InlineKeyboardButton().setText("Год").setCallbackData("CallYear");
        InlineKeyboardButton findByName = new InlineKeyboardButton().setText("Название").setCallbackData("CallName");
        InlineKeyboardButton findByRate = new InlineKeyboardButton().setText("Рейтинг").setCallbackData("CallRate");
        InlineKeyboardButton findByPicture = new InlineKeyboardButton().setText("Картинка").setCallbackData("CallPicture");
        InlineKeyboardButton findByAllParams = new InlineKeyboardButton().setText("Объеденить поиск").setCallbackData("CallAllSearchTypes");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(findByName);
        keyboardButtonsRow1.add(findByYear);
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(findByType);
        keyboardButtonsRow2.add(findByRate);
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        keyboardButtonsRow3.add(findByPicture);
        List<InlineKeyboardButton> keyboardButtonsRow4 = new ArrayList<>();
        keyboardButtonsRow4.add(findByAllParams);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);
        rowList.add(keyboardButtonsRow4);

        return inlineKeyboardMarkup.setKeyboard(rowList);
    }
}
