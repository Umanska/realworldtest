package org.realworld.api.datamodel.requests;

public class NewUserRequest {

    private NewUser user;

    public NewUserRequest(NewUser newUser){
        this.user = newUser;
    }

    public NewUser getUser() {
        return user;
    }
}
