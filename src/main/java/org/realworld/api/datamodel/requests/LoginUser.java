package org.realworld.api.datamodel.requests;

import static org.realworld.api.Constants.PASSWORD;
import static org.realworld.api.Constants.USER_1;

public class LoginUser {

    private String email;
    private String password;

    public LoginUser(String loginEmail, String loginPassword) {
        this.email = loginEmail;
        this.password = loginPassword;
    }

    public LoginUser() {
        this(USER_1, PASSWORD);
    }

    public LoginUser(String loginEmail) {
        this(loginEmail, PASSWORD);
    }
}
