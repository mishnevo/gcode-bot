package com.gcode.transfer.adapter;

import com.gcode.transfer.app.MessageHandlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;

@Component
@Slf4j
@RequiredArgsConstructor
public class TelegramBot implements SpringLongPollingBot {
    private final BotProperties botProperties;
    private final ApplicationContext context;

    @Override
    public String getBotToken() {
        return botProperties.token();
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return context.getBean(MessageHandlerService.class);
    }
}
