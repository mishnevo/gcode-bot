package com.gcode.transfer.app;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.MaybeInaccessibleMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.List;

import static com.gcode.transfer.app.IncomingMessage.MessageType.CALLBACK;

@Getter
public class IncomingMessage {
    private final MessageType type;
    private final Message message;
    private final Update update;
    private MaybeInaccessibleMessage callback;

    public IncomingMessage(Update update) {
        this.update = update;
        if (isMessage(update)) {
            if (isPhotoMessage(update)) {
                type = MessageType.PHOTO;
            } else if (isTextMessage(update)) {
                type = MessageType.MESSAGE;
            } else {
                type = MessageType.UNKNOWN;
            }
            message = update.getMessage();
        } else if (update.hasCallbackQuery()) {
            type = CALLBACK;
            callback = update.getCallbackQuery().getMessage();
            message = new Message();
            message.setText(update.getCallbackQuery().getData());
        } else if (update.hasEditedMessage()) {
            type = MessageType.EDITED_MESSAGE;
            message = update.getEditedMessage();
        } else {
            System.out.println(update.getMessage().getForumTopicCreated());
            type = MessageType.UNKNOWN;
            message = new Message();
        }
        if (message.getText() == null) {
            message.setText(" ");
        }
    }

    public Long getChatId() {
        if (type == CALLBACK) {
            return callback.getChatId();
        }
        return message.getChatId();
    }

    public enum MessageType {
        CALLBACK, MESSAGE, EDITED_MESSAGE, UNKNOWN, CHAT_MEMBER, PHOTO
    }

    public long getId() {
        return this.message.getMessageId();
    }

    public String getUsername() {
        return this.message.getFrom().getUserName();
    }

    public Boolean hasText() {
        if (this.type == MessageType.MESSAGE) {
            return this.message.hasText();
        } else if (this.type == MessageType.PHOTO) {
            return !(this.message.getCaption() == null);
        }
        return false;
    }

    public String getText() {
        if (this.type == MessageType.MESSAGE) {
            return this.message.getText();
        } else if (this.type == MessageType.PHOTO) {
            return this.message.getCaption() == null ? " " : this.message.getCaption();
        }
        return " ";
    }

    public long getParentMessageId() {
        return this.message.getReplyToMessage().getMessageId();
    }

    public long getFromId() {
        return this.message.getFrom().getId();
    }

    public int getThreadId() {
        return this.message.getMessageThreadId();
    }

    public List<PhotoSize> getPhoto() {
        return this.message.getPhoto();
    }

    public Message getParentMessage() {
        return this.message.getReplyToMessage();
    }

    public Long getParentMessageUserId() {
        return getParentMessage().getFrom().getId();
    }

    public boolean isTopicMessage() {
        return this.message.isTopicMessage();
    }

    public boolean isMessage() {
        return this.type.equals(MessageType.MESSAGE)
                || this.type.equals(MessageType.PHOTO);
    }

    public boolean isTextMessage() {
        return this.type.equals(MessageType.MESSAGE);
    }

    public boolean isPhotoMessage() {
        return this.type.equals(MessageType.PHOTO);
    }

    public boolean isReply() {
        return (!this.isTopicMessage() && this.message.isReply())
                || (this.isTopicMessage() && !(getParentMessageId() == 0 || getParentMessageId() == this.getThreadId()));
    }

    public boolean hasCallbackQuery() {
        return update.hasCallbackQuery();
    }

    public String getCallbackQuery() {
        return update.getCallbackQuery().getData();
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

    private boolean isMessage(Update update) {
        return update.hasMessage();
    }

    private boolean isTextMessage(Update update) {
        return update.getMessage().hasText();
    }

    private boolean isPhotoMessage(Update update) {
        return update.getMessage().hasPhoto();
    }
}
