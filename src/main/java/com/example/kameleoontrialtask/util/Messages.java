package com.example.kameleoontrialtask.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Messages {

    USER_ALREADY_EXISTS("User already exists"),
    USER_NOT_FOUND("User was not found"),
    QUOTES_NOT_FOUND("Quotes was not found"),
    QUOTE_NOT_FOUND("Quote was not found"),
    NOT_ENOUGH_QUOTES("There are not enough quotes in the database to complete the request");

    private final String message;
    public String getMessage() {
        return message;
    }
}
