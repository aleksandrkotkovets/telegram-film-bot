package com.telegram.film_bot.utils;

import com.google.common.collect.Lists;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.stream.Collectors;

public class KeyboardHelper {

    public static List<List<InlineKeyboardButton>> getRowsFromInlineKeyboardButtonList(List<InlineKeyboardButton> buttons, int splitSize) {
        return Lists.partition(buttons, splitSize);
    }

    public static List<InlineKeyboardButton> getInlineKeyboardButtonList(List<String> params) {
        return params.stream().map(param -> new InlineKeyboardButton().setText(param).setCallbackData(param)).collect(Collectors.toList());
    }
}
