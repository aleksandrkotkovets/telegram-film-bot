package com.telegram.film_bot.botapi.handler.callbackquery;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CallbackQueryType {
    YES, NO,
    GENRE, YEAR, RATE, COUNTRY
}
