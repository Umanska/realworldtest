package org.realworld.api;

import org.realworld.api.datamodel.requests.LoginUser;
import org.realworld.api.datamodel.requests.LoginUserRequest;
import org.realworld.api.datamodel.requests.NewUser;
import org.realworld.api.datamodel.requests.NewUserRequest;
import org.realworld.api.datamodel.responses.User;
import org.realworld.api.datamodel.responses.UserResponse;
import org.realworld.api.services.ApiService;
import org.realworld.utils.ResponseHandler;
import retrofit2.Response;

import static org.realworld.api.Constants.PASSWORD;

public class Session {

    private UserResponse user;

    public Session() {

    }

    public Session(UserResponse user) {
        this.user = user;
    }

    public User getUser() {
        return this.user.getUser();
    }

    public String getToken() {
        return this.user.getUser().getToken();
    }

    public String getEmail() {
        return this.user.getUser().getEmail();
    }

    public Session createSessionWithEmail(String email, ApiService serviceClass) {
        Response<UserResponse> rawResponse = signInUserWithEmail(email, serviceClass);
        if (!rawResponse.isSuccessful()) {
            rawResponse = signUpUserWithEmail(email, serviceClass);
        }
        return new Session(ResponseHandler.getParsedResponse(rawResponse).getResponseBody());
    }

    public Response<UserResponse> signUpUserWithEmail(String email, ApiService serviceClass) {
        if (email == null) {
            throw new RuntimeException("Email must be specified for sign up process");
        }
        NewUserRequest newUser = new NewUserRequest(new NewUser.Builder()
                .email(email)
                .username(email)
                .password(PASSWORD)
                .build());
        return ResponseHandler.executeSuccessfully(serviceClass.signUpUser(newUser));
    }

    public Response<UserResponse> signInUserWithEmail(String email, ApiService serviceClass) {
        LoginUserRequest userForSignIn = new LoginUserRequest(new LoginUser(email, PASSWORD));
        return ResponseHandler.execute(serviceClass.loginUser(userForSignIn));
    }
}
