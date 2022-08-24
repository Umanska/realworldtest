package org.realworld.api.datamodel.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateUserRequest {

    @JsonProperty
    private UpdateUser user;

    public UpdateUserRequest(UpdateUser updateUser){
        this.user = updateUser;
    }
}
