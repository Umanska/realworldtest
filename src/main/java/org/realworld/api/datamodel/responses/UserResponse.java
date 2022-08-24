package org.realworld.api.datamodel.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResponse {

    @JsonProperty
    private User user;

    public User getUser() {
        return user;
    }
}
