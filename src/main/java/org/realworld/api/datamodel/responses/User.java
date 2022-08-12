package org.realworld.api.datamodel.responses;

public class User {

    private String email;
    private String token;
    private String username;
    private String bio;
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
