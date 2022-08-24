package org.realworld.api.datamodel.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @JsonProperty
    private String email;
    @JsonProperty
    private String token;
    @JsonProperty
    private String username;
    @JsonProperty
    private String bio;
    @JsonProperty
    private String image;

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getImage() {
        return image;
    }

    public String getBio() {
        return bio;
    }
}
