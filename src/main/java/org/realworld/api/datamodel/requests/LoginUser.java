package org.realworld.api.datamodel.requests;

import static org.realworld.api.Constants.PASSWORD;
import static org.realworld.api.Constants.USER_1_EMAIL;

public class LoginUser {

    private String email;
    private String password;

    public LoginUser(String loginEmail, String loginPassword) {
        this.email = loginEmail;
        this.password = loginPassword;
    }

    /**
     * Create LoginUser object with default email and password
     */
    public LoginUser() {
        this(USER_1_EMAIL, PASSWORD);
    }

    /**
     * Create LoginUser object with default password
     *
     * @param loginEmail user email for signing in, example "username@gmail.com"
     */
    public LoginUser(String loginEmail) {
        this(loginEmail, PASSWORD);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
