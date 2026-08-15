package com.example.capitalmarkets.tradesettlement.config;

import com.example.capitalmarkets.tradesettlement.auth.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {
}
