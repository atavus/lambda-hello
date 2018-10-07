package org.atavus.demo.aws.lambda;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class APIResponse<T> {
    private int statusCode;
    private Map<String, String> headers;
    private String body;
    private boolean isBase64Encoded;
    private Exception exception;

    public APIResponse<T> withStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public APIResponse<T> withHeader(String key, String value) {
        if (headers == null)
            headers = new HashMap<String, String>();
        headers.put(key, value);
        return this;
    }

    public APIResponse<T> withBody(T body) {
        this.body = new Mapper().writeValueAsString(body);
        return this;
    }

    public APIResponse<T> isBase64Encoded(boolean isBase64Encoded) {
        this.isBase64Encoded = isBase64Encoded;
        return this;
    }

    public APIResponse<T> withException(Exception exception) {
        this.exception = exception;
        return this;
    }

}
