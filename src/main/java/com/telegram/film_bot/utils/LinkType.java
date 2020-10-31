package com.telegram.film_bot.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LinkType {
    DELETE("delete");

    private String type;

}
