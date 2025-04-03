package com.gcode.transfer.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Lazy
public class MessageHandlerService implements LongPollingUpdateConsumer {
    private final List<MessageHandler> messageHandlers;

    @Async
    @Override
    public void consume(List<Update> updates) {
        log.info(updates.toString());
        updates.forEach(update -> {
                    var message = new IncomingMessage(update);
                    messageHandlers.stream()
                            .peek(it -> System.out.println("Testing " + it.getClass()))
                            .filter(messageHandler -> messageHandler.canHandle(message))
                            .findFirst().ifPresent(handler -> {
                                log.info("Message will be handled by " + handler.getClass().getName());
                                for (int i = 0; i < 3; i++) {
                                    if (handler.handle(message)) {
                                        break;
                                    }
                                }
                            });
                }
        );
    }

}

