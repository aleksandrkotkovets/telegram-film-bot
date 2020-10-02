package com.telegram.film_bot;

import com.telegram.film_bot.botapi.TelegramFacade;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;


@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilmTelegramBot extends TelegramWebhookBot {

    String botPath;
    String botUsername;
    String botToken;

    private TelegramFacade telegramFacade;

    public FilmTelegramBot(TelegramFacade telegramFacade) {
        this.telegramFacade = telegramFacade;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return telegramFacade.handleUpdate(update);
    }

    public void forwardMessage(List<ForwardMessage> forwardMessageList) {
        forwardMessageList.forEach(forwardMessage -> {
            try {
                String fromChatId = forwardMessage.getFromChatId();
                forwardMessage.setFromChatId('-' + fromChatId);
                execute(forwardMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        });
    }

    public void setCommands(List<BotCommand> botCommands){
        SetMyCommands myCommands = new SetMyCommands(botCommands);
        try {
            execute(myCommands);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
