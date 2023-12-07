package com.example.kameleoontrialtask.services;

import com.example.kameleoontrialtask.dto.UserCreateRequest;
import com.example.kameleoontrialtask.entities.User;
import com.example.kameleoontrialtask.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.kameleoontrialtask.util.Messages.USER_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public User createUser(UserCreateRequest request) {

        if (userRepo.existsByEmail(request.getEmail()))
            throw new IllegalArgumentException(USER_ALREADY_EXISTS.getMessage());

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encodedPassword).build();

        userRepo.save(user);
        return user;
    }
}
