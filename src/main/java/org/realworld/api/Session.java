package org.realworld.api;

import org.realworld.api.datamodel.responses.UserResponse;

public class Session {

    private UserResponse user;

    public Session(UserResponse user) {
        this.user = user;
    }

    public String getToken() {
        return this.user.getUser().getToken();
    }

    public String getEmail() {
        return this.user.getUser().getEmail();
    }
}
