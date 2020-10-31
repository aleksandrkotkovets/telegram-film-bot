package com.telegram.film_bot.botapi.handler;

public enum BotState {
    SHOW_MAIN_MENU,
    SHOW_HELP_MENU,
    SHOW_RANDOM_FILM,

    RANDOM_FILM_ASK,
    RANDOM_FILM_SHOW_ASK_PARAM_KEYBOARD,
    RANDOM_FILM_CHOOSE_GENRE,
    RANDOM_FILM_CHOOSE_COUNTRY,
    RANDOM_FILM_CHOOSE_RATE,
    RANDOM_FILM_CHOOSE_YEAR,
    GET_RANDOM_FILM,
    GET_RANDOM_FILM_BY_PARAM,

    SHOW_TOP_FILMS,
    GET_TOP_FILMS,

    FIND_FILM,
    FIND_FILM_STARTED,

    RECOMMEND_FILM,
    ASK_RECOMMEND_FILM,
    ADD_RECOMMEND_FILM,
    DELETE_RECOMMEND_FILM,

    SHOW_RECOMMENDATIONS_UNREAD,
    GET_RECOMMENDATIONS_UNREAD,

    SHOW_RECOMMENDATIONS_ALL,
    GET_RECOMMENDATIONS_ALL

}
