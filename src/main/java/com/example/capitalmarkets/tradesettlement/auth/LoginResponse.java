package com.example.capitalmarkets.tradesettlement.auth;

public record LoginResponse(
        String accessToken,
        String tokenType
) {
}
