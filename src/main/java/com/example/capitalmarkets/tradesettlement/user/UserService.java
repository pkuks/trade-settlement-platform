package com.example.capitalmarkets.tradesettlement.user;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);

    List<User> findAll();
}
