package org.realworld.api.datamodel.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewUserRequest {

    @JsonProperty
    private NewUser user;

    public NewUserRequest(NewUser newUser){
        this.user = newUser;
    }

    public NewUser getUser() {
        return user;
    }
}
