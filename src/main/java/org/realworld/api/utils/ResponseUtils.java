package org.realworld.api.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.realworld.api.datamodel.responses.ResponseWrapper;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class ResponseUtils {

    public static <T> ResponseWrapper<T> executeAndParse(Call<T> request) {
        Response<T> rawResponse = executeSuccessfully(request);
        return parse(rawResponse);
    }

    public static <T> ResponseWrapper<String> executeAndParseError(Call<T> request) {
        Response<T> rawResponse = execute(request);
        return parsedError(rawResponse);
    }

    public static <T> ResponseWrapper<T> parse(Response<T> rawResponse) {

        if (rawResponse.body() == null) {
            throw new RuntimeException("There is no responseBody to handle, status code = " + rawResponse.code());
        }
        return new ResponseWrapper<>(rawResponse.code(), rawResponse.body());
    }

    public static <T> ResponseWrapper<String> parsedError(Response<T> rawResponse) {

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
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode responseAsJson = objectMapper.readTree(rawResponse.errorBody().charStream());
            String errorMessage = responseAsJson.get("errors").get("body").get(0).asText();
            return new ResponseWrapper<>(rawResponse.code(), errorMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Response<T> execute(Call<T> request) {
        try {
            return request.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Response<T> executeSuccessfully(Call<T> request) {
        try {
            Response<T> response = request.execute();
            if (!response.isSuccessful()) {
                throw new RuntimeException("Unexpected status code for this request: " + response.code());
            }
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
