package org.realworld.api.datamodel.responses;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class User {

    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String token;
    @NotBlank
    private String username;
    @NotNull
    private String bio;
    @NotNull
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
