package com.gcode.transfer.adapter;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.Serializable;
import java.util.Optional;

@Component
public class TelegramGateway {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TelegramGateway.class);
    private final TelegramClient telegramClient;

    public TelegramGateway(TelegramBot telegramBot) {
        this.telegramClient = new OkHttpTelegramClient(telegramBot.getBotToken());
    }

    public Optional<Message> send(SendMessage sendMessage) {
        try {
            var message = telegramClient.execute(sendMessage);
            return Optional.of(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<Serializable> send(EditMessageText editMessageText) {
        try {
            var message = telegramClient.execute(editMessageText);
            return Optional.of(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<Message> send(SendPhoto sendPhoto) {
        try {
            var message = telegramClient.execute(sendPhoto);
            return Optional.of(message);
        } catch (TelegramApiException e) {
            if (e.getMessage().contains("thread")) {
                return Optional.empty();
            }
            log.error(e.getMessage());
            return Optional.empty();
        }
    }
}
