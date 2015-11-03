package com.github.rojanu.contact.rest.core;

public class ExampleResponse {
    private int code;
    private String message;

    public ExampleResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
