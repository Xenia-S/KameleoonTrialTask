package com.example.kameleoontrialtask.controllers;

import com.example.kameleoontrialtask.dto.UserCreateRequest;
import com.example.kameleoontrialtask.entities.User;
import com.example.kameleoontrialtask.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public User createUser(@RequestBody @Valid UserCreateRequest request) {
        return userService.createUser(request);
    }
}
