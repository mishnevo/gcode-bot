package com.gcode.transfer.app;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.List;

@Component
public class KeyboardService {
    public static InlineKeyboardButton newButton(String callbackData, String text) {
        var button = new InlineKeyboardButton(text);
        button.setCallbackData(callbackData);
        return button;
    }

    public static InlineKeyboardRow createRow(InlineKeyboardButton... buttons) {
        return new InlineKeyboardRow(List.of(buttons));
    }
}
