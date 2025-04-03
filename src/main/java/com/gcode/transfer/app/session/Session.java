package com.gcode.transfer.app.session;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Session {
    private Long id;
    private String username;
    private String login;
    private SessionState state;
}

