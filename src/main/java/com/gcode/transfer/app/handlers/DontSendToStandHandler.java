package com.gcode.transfer.app.handlers;


import com.gcode.transfer.adapter.TelegramGateway;
import com.gcode.transfer.app.IncomingMessage;
import com.gcode.transfer.app.MessageHandler;
import com.gcode.transfer.app.session.SessionProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.gcode.transfer.app.session.SessionState.SIMPLE_CODE;
import static com.gcode.transfer.app.session.SessionState.VECTOR_PICTURE;

@Component
@RequiredArgsConstructor
@Slf4j
public class DontSendToStandHandler implements MessageHandler {
    private final SessionProvider sessionProvider;
    private final TelegramGateway telegramGateway;
    @Override
    public boolean handle(IncomingMessage message) {
        telegramGateway.send(new SendMessage(message.getChatId().toString(), "Снова отправь мне Simple Code"));
        sessionProvider.updateSessionState(message.getChatId(), SIMPLE_CODE);
        return true;
    }

    @Override
    public boolean canHandle(IncomingMessage message) {
        if (sessionProvider.getSession(message.getChatId()).getState() == VECTOR_PICTURE) {
            return message.getMessage().getText().equals("no");
        }
        return false;
    }
}
