package com.telegram.film_bot.botapi.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.ArrayList;
import java.util.List;

@Component
public class FilmBotCommand {

    private List<BotCommand> createBotMainCommand() {
        List<BotCommand> botCommands = new ArrayList<>();

        BotCommand botCommand = new BotCommand("/start", "Показать главное меню");
        botCommands.add(botCommand);

        botCommand = new BotCommand("/find", "Найти фильм. Сокращенно: найти, /find, поиск");
        botCommands.add(botCommand);

        botCommand = new BotCommand("/random", "Получить рандомный фильм по одному из критериев");
        botCommands.add(botCommand);

        botCommand = new BotCommand("/top_5", "Узнать топ-5 фильмов на канале");
        botCommands.add(botCommand);

        botCommand = new BotCommand("/recommend", "Вы можете посоветовать фильм боту, чтобы он отправил их автору канала на просмотр");
        botCommands.add(botCommand);

        botCommand = new BotCommand("/help", "Получить помощь по коммандам и узнать более подробную информацию");
        botCommands.add(botCommand);

        return botCommands;
    }

    public List<BotCommand> createBotCommandForUser() {
        return createBotMainCommand();
    }

    public List<BotCommand> createBotCommandForAdmin() {
        List<BotCommand> botMainCommand = createBotMainCommand();

        BotCommand botCommand = new BotCommand("/recommendations_unread", "Показать все непрочитанные сообщения с рекоммендациями");
        botMainCommand.add(botCommand);

        botCommand = new BotCommand("/recommendations", "Показать все рекоммендации");
        botMainCommand.add(botCommand);

        return botMainCommand;
    }

}
