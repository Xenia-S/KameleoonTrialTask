package com.example.kameleoontrialtask.repositories;

import com.example.kameleoontrialtask.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
}
