package org.realworld.api.datamodel.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewUser {

    @JsonProperty
    private String username;
    @JsonProperty
    private String email;
    @JsonProperty
    private String password;


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public static class Builder {
        private final NewUser user = new NewUser();

        public Builder username(String username) {
            user.username = username;
            return this;
        }

        public Builder email(String email) {
            user.email = email;
            return this;
        }

        public Builder password(String password) {
            user.password = password;
            return this;
        }

        public NewUser build() {
            if (user.username == null || user.email == null || user.password == null) {
                throw new RuntimeException("Specify all required fields: username, email and password");
            }
            return user;
        }
    }
}
