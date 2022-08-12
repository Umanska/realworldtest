package org.realworld.api.services;

import org.realworld.api.datamodel.requests.LoginUserRequest;
import org.realworld.api.datamodel.requests.NewUserRequest;
import org.realworld.api.datamodel.requests.UpdateUserRequest;
import org.realworld.api.datamodel.responses.UserResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiService {

    @POST("/dev/api/users")
    Call<UserResponse> signUpUser(@Body NewUserRequest newUser);

    @POST("/dev/api/users/login")
    Call<UserResponse> loginUser(@Body LoginUserRequest loginUser);

    @GET("/dev/api/user")
    Call<UserResponse> getUser();

    @PUT("/dev/api/user")
    Call<UserResponse> updateUser(@Body UpdateUserRequest updateUser);
}
