package org.realworld.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.realworld.api.datamodel.responses.ResponseWrapper;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class ResponseHandler {

    public static <T> ResponseWrapper<T> executeAndGetParsedResponse(Call<T> request) {
        Response<T> rawResponse = executeSuccessfully(request);
        return getParsedResponse(rawResponse);
    }

    public static <T> ResponseWrapper<String> executeAndGetParsedErrorResponse(Call<T> request) {
        Response<T> rawResponse = execute(request);
        return getParsedErrorResponse(rawResponse);
    }

    public static <T> ResponseWrapper<T> getParsedResponse(Response<T> rawResponse) {

        if (rawResponse.body() == null) {
            throw new RuntimeException("There is no responseBody to handle, status code = " + rawResponse.code());
        }
        return new ResponseWrapper<>(rawResponse.code(), rawResponse.body());
    }

    public static <T> ResponseWrapper<String> getParsedErrorResponse(Response<T> rawResponse) {

        if (rawResponse.code() >= 500) {
            try {
                return new ResponseWrapper<>(rawResponse.code(), rawResponse.errorBody().string());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (rawResponse.errorBody() == null) {
            return new ResponseWrapper<>(rawResponse.code(), null);
        }
        Gson gson = new Gson();
        JsonObject response = gson.fromJson(rawResponse.errorBody().charStream(), JsonObject.class);
        String errorMessage = response
                .getAsJsonObject("errors")
                .getAsJsonArray("body")
                .get(0).getAsString();
        return new ResponseWrapper<>(rawResponse.code(), errorMessage);
    }

    public static <T> Response<T> execute(Call<T> request) {
        Response<T> response;
        try {
            response = request.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public static <T> Response<T> executeSuccessfully(Call<T> request) {
        Response<T> response;
        try {
            response = request.execute();
            if (!response.isSuccessful()) {
                throw new RuntimeException("Unexpected status code for this request: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
