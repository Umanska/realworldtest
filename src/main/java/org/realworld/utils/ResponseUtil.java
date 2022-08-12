package org.realworld.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.realworld.api.datamodel.responses.ResponseWrapper;
import retrofit2.Response;

import java.io.IOException;

public class ResponseUtil {

    public static <T> ResponseWrapper<T> getParsedResponse(Response<T> rawResponse) {
        if (rawResponse == null) {
            throw new RuntimeException("There is no response to handle");
        }
        if (rawResponse.body() == null) {
            throw new RuntimeException("There is no responseBody to handle, status code = " + rawResponse.code());
        }
        return new ResponseWrapper<>(rawResponse.code(), rawResponse.body());
    }

    public static <T> ResponseWrapper<String> getParsedErrorResponse(Response<T> rawResponse) throws IOException {
        if (rawResponse == null) {
            throw new RuntimeException("There is no response to handle");
        }
        if (rawResponse.errorBody() == null) {
            return new ResponseWrapper<>(rawResponse.code(), null);
        }
        if (rawResponse.code() >= 500) {
            return new ResponseWrapper<>(rawResponse.code(), rawResponse.errorBody().string());
        }

        Gson gson = new Gson();
        JsonObject response = gson.fromJson(rawResponse.errorBody().charStream(), JsonObject.class);
        String errorMessage = response
                .getAsJsonObject("errors")
                .getAsJsonArray("body")
                .get(0).getAsString();
        return new ResponseWrapper<>(rawResponse.code(), errorMessage);
    }
}
