package com.example.capitalmarkets.tradesettlement.config;

import com.example.capitalmarkets.tradesettlement.auth.CustomUserDetailsService;
import com.example.capitalmarkets.tradesettlement.auth.JwtAuthenticationFilter;
import com.example.capitalmarkets.tradesettlement.auth.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        //provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, userDetailsService);
        http
                // 1. Disable CSRF (usually necessary for stateless REST APIs / JWT)
                .csrf(csrf -> csrf.disable())

                // 2. Configure endpoint authorization
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Allow public access to login/register
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/trades").hasAnyRole("ADMIN", "TRADER")
                        .requestMatchers(HttpMethod.GET, "/api/trades/**").hasAnyRole("ADMIN","TRADER","OPERATIONS")
                        .requestMatchers(HttpMethod.PATCH, "api/trades/*/validate").hasAnyRole("ADMIN", "OPERATIONS")
                        .requestMatchers(HttpMethod.PATCH, "api/trades/*/ready-for-settlement").hasAnyRole("ADMIN", "OPERATIONS")
                        .requestMatchers(HttpMethod.POST, "/api/trades/*/settlements").hasAnyRole("ADMIN", "OPERATIONS")
                        .anyRequest().authenticated()  // Protect all other endpoints
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                // 3. Configure authentication provider
                //.authenticationProvider(authenticationProvider());

        return http.build();
    }
}
