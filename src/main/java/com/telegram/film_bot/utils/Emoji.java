package com.telegram.film_bot.utils;


import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum  Emoji {

    ONE(EmojiParser.parseToUnicode(":one:")),
    TWO(EmojiParser.parseToUnicode(":two:")),
    SOUND(EmojiParser.parseToUnicode(":sound:")),
    INFORMATION_SOURCE(EmojiParser.parseToUnicode(":information_source:")),
    WARNING(EmojiParser.parseToUnicode(":warning:")),
    CHOCOLATE_BAR(EmojiParser.parseToUnicode(":chocolate_bar:")),
    DOUGHNUT(EmojiParser.parseToUnicode(":doughnut:")),
    COOKIE(EmojiParser.parseToUnicode(":cookie:")),
    HONEY_POT(EmojiParser.parseToUnicode(":honey_pot:")),
    CINEMA(EmojiParser.parseToUnicode(":cinema:")),
    MAG_RIGHT(EmojiParser.parseToUnicode(":mag_right:")),
    MEMO(EmojiParser.parseToUnicode(":memo:")),
    EXCLAMATION(EmojiParser.parseToUnicode(":exclamation:")),
    ARROW_DOWN(EmojiParser.parseToUnicode(":arrow_down:")),
    ARROW_UP(EmojiParser.parseToUnicode(":arrow_up:")),
    POPCORN(EmojiParser.parseToUnicode(":popcorn:")),
    RELAXED(EmojiParser.parseToUnicode(":relaxed:")),
    ARROWS_COUNTERCLOCKWISE(EmojiParser.parseToUnicode(":arrow_counterclockwise:"));

    private String emojiName;

    @Override
    public String toString() {
        return emojiName;
    }
}
