package org.realworld.services;

import org.realworld.api.Session;
import org.realworld.api.datamodel.requests.LoginUser;
import org.realworld.api.datamodel.requests.LoginUserRequest;
import org.realworld.api.datamodel.requests.NewUser;
import org.realworld.api.datamodel.requests.NewUserRequest;
import org.realworld.api.datamodel.responses.UserResponse;
import org.realworld.api.services.ApiService;
import org.realworld.utils.ResponseUtils;
import retrofit2.Response;

import static org.realworld.api.Constants.PASSWORD;

public class AuthenticationService {

    private ApiService serviceClass;

    public AuthenticationService(ApiService serviceClass) {
        this.serviceClass = serviceClass;
    }

    public Session createSessionWithEmail(String email) {
        Response<UserResponse> rawResponse = signInUserWithEmail(email);
        if (!rawResponse.isSuccessful()) {
            rawResponse = signUpUserWithEmail(email);
        }
        return new Session(ResponseUtils.parse(rawResponse).getResponseBody());
    }

    public Response<UserResponse> signUpUserWithEmail(String email) {
        if (email == null) {
            throw new RuntimeException("Email must be specified for sign up process");
        }
        NewUserRequest newUser = new NewUserRequest(new NewUser.Builder()
                .email(email)
                .username(email)
                .password(PASSWORD)
                .build());
        return ResponseUtils.executeSuccessfully(serviceClass.signUpUser(newUser));
    }

    public Response<UserResponse> signInUserWithEmail(String email) {
        LoginUserRequest userForSignIn = new LoginUserRequest(new LoginUser(email, PASSWORD));
        return ResponseUtils.execute(serviceClass.loginUser(userForSignIn));
    }
}
