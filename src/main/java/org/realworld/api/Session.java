package org.realworld.api;

import org.realworld.api.datamodel.responses.User;
import org.realworld.api.datamodel.responses.UserResponse;

public class Session {

    private UserResponse user;

    public Session() {

    }

    public Session(UserResponse user) {
        this.user = user;
    }

    public User getUser() {
        return this.user.getUser();
    }

    public String getToken() {
        return this.user.getUser().getToken();
    }

    public String getEmail() {
        return this.user.getUser().getEmail();
    }

}
