package com.gcode.transfer.app.handlers;

import com.gcode.transfer.adapter.BackOutbound;
import com.gcode.transfer.adapter.TelegramGateway;
import com.gcode.transfer.app.ImageService;
import com.gcode.transfer.app.IncomingMessage;
import com.gcode.transfer.app.KeyboardService;
import com.gcode.transfer.app.MessageHandler;
import com.gcode.transfer.app.session.SessionProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.io.File;
import java.util.List;

import static com.gcode.transfer.app.session.SessionState.SIMPLE_CODE;
import static com.gcode.transfer.app.session.SessionState.VECTOR_PICTURE;

@Component
@RequiredArgsConstructor
public class SimpleCodeHandler implements MessageHandler {
    private final SessionProvider sessionProvider;
    private final BackOutbound backOutbound;
    private final TelegramGateway telegramGateway;
    private final ImageService imageService;

    @Override
    public boolean handle(IncomingMessage message) {
        var svg = backOutbound.sendSimpleCode(message.getText());
        try {
            var png = imageService.convertSvgStringToPng(svg);
            var photoMessage = buildMessage(message.getChatId().toString(), "Это?", png, message.getText());
            telegramGateway.send(photoMessage);
            sessionProvider.updateSessionState(message.getChatId(), VECTOR_PICTURE);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean canHandle(IncomingMessage message) {
        return sessionProvider.getSession(message.getChatId()).getState() == SIMPLE_CODE;
    }


    public SendPhoto buildMessage(String chatId, String text, File file, String simpleCode) {
        var input = new InputFile(file);
        var sendPhoto = new SendPhoto(chatId, input);
        var yesButton = KeyboardService.newButton("yes/" + simpleCode, "Да");
        var noButton = KeyboardService.newButton("no", "Нет");
        var keyboard = new InlineKeyboardMarkup(List.of(KeyboardService.createRow(yesButton, noButton)));
        sendPhoto.setCaption(text);
        sendPhoto.setReplyMarkup(keyboard);
        return sendPhoto;
    }
}
