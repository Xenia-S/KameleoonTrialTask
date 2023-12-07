package com.example.kameleoontrialtask.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Messages {

    USER_ALREADY_EXISTS("User already exists");

    private final String message;
    public String getMessage() {
        return message;
    }
}
