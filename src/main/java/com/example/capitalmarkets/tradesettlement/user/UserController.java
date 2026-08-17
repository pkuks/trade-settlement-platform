package com.example.capitalmarkets.tradesettlement.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse createUser(
            @Valid @RequestBody CreateUserRequest request){
        return userService.createUser(request);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.findAll();
    }
}
