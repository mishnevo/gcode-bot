package com.gcode.transfer.app.session;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

import static com.gcode.transfer.app.session.SessionState.INIT_STATE;

@Service
@RequiredArgsConstructor
public class SessionProvider {
    private final ConcurrentHashMap<Long, Session> sessions = new ConcurrentHashMap<>();

    public Session getSession(Long id) {
        if (this.sessions.containsKey(id)) {
            return this.sessions.get(id);
        }
        var session = new Session()
                .setId(id)
                .setState(INIT_STATE);

        this.sessions.put(id, session);
        return session;
    }

    public void updateSession(Long id, Session session) {
        this.sessions.put(id, session);
    }

    public void updateSessionState(Long id, SessionState state) {
        var session = this.sessions.get(id);
        session.setState(state);
        this.sessions.put(id, session);
    }

    public void clear() {
        this.sessions.clear();
    }
}
