package com.example.capitalmarkets.tradesettlement.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record CreateUserRequest(
        @NotBlank
        String username,

        @NotBlank
        @Size(min=8)
        String password,

        @NotEmpty
        Set<String> roles) {
}
