package com.example.capitalmarkets.tradesettlement.auth;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.jwt")
public record JwtProperties(
        String secretKey,
        long expirationMs) {
}
