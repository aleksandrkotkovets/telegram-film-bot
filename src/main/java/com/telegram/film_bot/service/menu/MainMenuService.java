package com.telegram.film_bot.service.menu;

import com.telegram.film_bot.FilmTelegramBot;
import com.telegram.film_bot.botapi.command.FilmBotCommand;
import com.telegram.film_bot.security.MenuKeyboardRestrictAccess;
import com.telegram.film_bot.service.keyboard.MenuKeyboard;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;

@Service
public class MainMenuService extends AbstractMenuService {

    private final MenuKeyboard menuKeyboard;
    private final MenuKeyboardRestrictAccess menuKeyboardRestrictAccess;
    private final FilmBotCommand filmBotCommand;
    private final FilmTelegramBot filmTelegramBot;

    public MainMenuService(MenuKeyboard menuKeyboard,
                           MenuKeyboardRestrictAccess menuKeyboardRestrictAccess,
                           FilmBotCommand filmBotCommand,
                           @Lazy FilmTelegramBot filmTelegramBot) {

        this.menuKeyboard = menuKeyboard;
        this.menuKeyboardRestrictAccess = menuKeyboardRestrictAccess;
        this.filmBotCommand = filmBotCommand;
        this.filmTelegramBot = filmTelegramBot;
    }

    public SendMessage getMainMenuMessage(Message message, String textMessage, Object... args) {

        ReplyKeyboardMarkup replyKeyboardMarkup;
        List<BotCommand> botCommands;
        List<Exception> exceptions = menuKeyboardRestrictAccess.validate(message.getFrom().getId());
        if (!exceptions.isEmpty()) {
            replyKeyboardMarkup = menuKeyboard.getMainMenuKeyboardUser();
            botCommands = filmBotCommand.createBotCommandForUser();
        } else {
            replyKeyboardMarkup = menuKeyboard.getMainMenuKeyboardAdmin();
            botCommands = filmBotCommand.createBotCommandForAdmin();
        }
        filmTelegramBot.setCommands(botCommands);
        return createMessageWithKeyboard(message.getChatId(), textMessage, replyKeyboardMarkup);
    }

}
