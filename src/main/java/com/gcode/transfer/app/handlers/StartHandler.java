package com.gcode.transfer.app.handlers;

import com.gcode.transfer.adapter.TelegramGateway;
import com.gcode.transfer.app.IncomingMessage;
import com.gcode.transfer.app.MessageHandler;
import com.gcode.transfer.app.session.SessionProvider;
import com.gcode.transfer.app.session.SessionState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.gcode.transfer.app.session.SessionState.SIMPLE_CODE;

@Component
@RequiredArgsConstructor
public class StartHandler implements MessageHandler {
    private final SessionProvider sessionProvider;
    private final TelegramGateway telegramGateway;

    @Override
    public boolean handle(IncomingMessage message) {
        telegramGateway.send(buildStartMessage(message.getChatId()));
        sessionProvider.updateSessionState(message.getChatId(), SIMPLE_CODE);
        return true;
    }

    @Override
    public boolean canHandle(IncomingMessage message) {
        return sessionProvider.getSession(message.getChatId()).getState() == SessionState.INIT_STATE;
    }

    private SendMessage buildStartMessage(Long chatId) {
        return new SendMessage(chatId.toString(), "Привет! \uD83D\uDC4B Я – бот, который поможет тебе управлять лабораторным стендом с сервоприводами.\n" +
                "\n" +
                "Ты можешь отправить мне команду на специальном языке, и стенд выполнит её, нарисовав заданную фигуру. А я пришлю тебе изображение, чтобы ты мог проверить, что всё верно.\n" +
                "\n" +
                "Вот основные команды:\n" +
                "\uD83D\uDD3C НАВЕРХ <смещение по координате> – движение вверх\n" +
                "\uD83D\uDD3D ВНИЗ <смещение по координате> – движение вниз\n" +
                "▶\uFE0F НАПРАВО <смещение по координате> – движение вправо\n" +
                "◀\uFE0F НАЛЕВО <смещение по координате> – движение влево\n" +
                "⭕\uFE0F ДУГА+ / ДУГА- <координата конца по X> <координата конца по Y> <смещение от центра по X> <смещение от центра по Y> – движение по окружности по/против\n" +
                "\n" +
                "После каждой команды нужно поставить знак точки с запятой, а следующую команду писать с новой строки\n" +
                "\n" +
                "Попробуй отправить мне что-нибудь, и посмотрим, что получится! \uD83D\uDE0A");
    }
}
