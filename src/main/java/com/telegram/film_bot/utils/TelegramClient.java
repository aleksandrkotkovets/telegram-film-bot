package com.telegram.film_bot.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@Component
public class TelegramClient {

    @Value("${telegram.org-bot}")
    private String telegramOrgBot;

    @Value("${telegram.bot.botToken}")
    private String botToken;

    public void forwardFromChannelToUser(Long chatId, String channelId, Integer messageId) {
        String urlStr = telegramOrgBot + botToken + "/forwardMessage?chat_id=%s&from_chat_id=-%s&message_id=%s";
        urlStr = String.format(urlStr, chatId, channelId, messageId);
        urlStr = String.format(urlStr, 615340129);
        doRequest(urlStr);
    }

    public void doRequest(String urlStr) {
        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            InputStream is = new BufferedInputStream(conn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
