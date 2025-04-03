package com.gcode.transfer.app.session;

import lombok.Getter;

@Getter
public enum SessionState {
    INIT_STATE,
    SIMPLE_CODE,
    VECTOR_PICTURE,
    TO_STAND,
    IN_PROGRESS
}