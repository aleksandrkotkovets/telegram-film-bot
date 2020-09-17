package com.telegram.film_bot.service.keyboard;

import com.telegram.film_bot.botapi.handler.callbackquery.CallbackQueryType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.telegram.film_bot.utils.KeyboardHelper.getInlineKeyboardButtonList;
import static com.telegram.film_bot.utils.KeyboardHelper.getRowsFromInlineKeyboardButtonList;

@Component
public class Keyboard {

    @Value("${keyboard.split-size}")
    int splitSize;

    public InlineKeyboardMarkup getYesNoKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton randomYes = new InlineKeyboardButton().setText("Да").setCallbackData(String.valueOf(CallbackQueryType.YES));
        InlineKeyboardButton randomNo = new InlineKeyboardButton().setText("Нет").setCallbackData(String.valueOf(CallbackQueryType.NO));

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(randomYes);
        keyboardButtonsRow1.add(randomNo);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getFilmMainParamsKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton genre = new InlineKeyboardButton().setText("Жанр").setCallbackData(String.valueOf(CallbackQueryType.GENRE));
        InlineKeyboardButton year = new InlineKeyboardButton().setText("Год").setCallbackData(String.valueOf(CallbackQueryType.YEAR));
        InlineKeyboardButton rate = new InlineKeyboardButton().setText("Рейтинг").setCallbackData(String.valueOf(CallbackQueryType.RATE));

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(genre);
        keyboardButtonsRow1.add(year);

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(rate);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getParamsKeyboard(List<String> params) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttons = getInlineKeyboardButtonList(params);

        List<List<InlineKeyboardButton>> rows = getRowsFromInlineKeyboardButtonList(buttons, splitSize);

        inlineKeyboardMarkup.setKeyboard(rows);

        return inlineKeyboardMarkup;
    }


}
