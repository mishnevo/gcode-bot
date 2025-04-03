package com.gcode.transfer.app.handlers;

import com.gcode.transfer.adapter.BackOutbound;
import com.gcode.transfer.adapter.TelegramGateway;
import com.gcode.transfer.app.IncomingMessage;
import com.gcode.transfer.app.MessageHandler;
import com.gcode.transfer.app.session.SessionProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import static com.gcode.transfer.app.session.SessionState.SIMPLE_CODE;
import static com.gcode.transfer.app.session.SessionState.VECTOR_PICTURE;

@Component
@RequiredArgsConstructor
@Slf4j
public class SendToStandHandler implements MessageHandler {
    private final SessionProvider sessionProvider;
    private final TelegramGateway telegramGateway;
    private final BackOutbound backOutbound;

    @Override
    public boolean handle(IncomingMessage message) {
        telegramGateway.send(new SendMessage(message.getChatId().toString(), "Выполняется..."));
        var data = message.getMessage().getText().split("/");
        try {
            backOutbound.sendToStand(data[1]);
            sessionProvider.updateSessionState(message.getChatId(), SIMPLE_CODE);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean canHandle(IncomingMessage message) {
        if (sessionProvider.getSession(message.getChatId()).getState() == VECTOR_PICTURE) {
            var data = message.getMessage().getText().split("/");
            return data[0].equals("yes");
        }
        return false;
    }

    public EditMessageText editMessageText(String text, String chatId, Integer messageId) {
        var edit = new EditMessageText(text);
        edit.setMessageId(messageId);
        edit.setChatId(chatId);
        return edit;
    }
}
