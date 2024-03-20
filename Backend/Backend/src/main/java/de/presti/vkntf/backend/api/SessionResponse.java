package de.presti.vkntf.backend.api;

import de.presti.vkntf.backend.repository.UserBase;
import de.presti.vkntf.backend.repository.session.Session;

public class SessionResponse {

    public String sessionToken;
    public String fullName;

    public SessionResponse(Session session, UserBase user) {
        sessionToken = session.getSessionToken();
        fullName = user.getVorname() + " " + user.getNachname();
    }

}
