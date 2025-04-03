package com.gcode.transfer.app;

public interface MessageHandler {
    boolean handle(IncomingMessage message);
    boolean canHandle(IncomingMessage message);
}