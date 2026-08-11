package com.example.capitalmarkets.tradesettlement.user;

import java.util.Set;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        UserStatus status,
        Set<String> roles
) {
}
