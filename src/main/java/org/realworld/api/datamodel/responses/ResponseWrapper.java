package org.realworld.api.datamodel.responses;

public class ResponseWrapper<T> {

    private int statusCode;
    private T responseBody;

    public ResponseWrapper(int statusCode, T responseBody) {
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public T getResponseBody() {
        return responseBody;
    }
}
