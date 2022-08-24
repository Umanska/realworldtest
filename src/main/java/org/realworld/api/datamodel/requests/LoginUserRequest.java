package org.realworld.api.datamodel.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginUserRequest {
    @JsonProperty
    private LoginUser user;

    public LoginUserRequest(LoginUser user) {
        this.user = user;
    }
}
