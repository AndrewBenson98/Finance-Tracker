package com.benson.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for easier API testing
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll() // Allow access to H2 console without authentication
                        .anyRequest().permitAll() // This opens all your endpoints
                )
                
                //Allows for h2 console UI
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Strength of 10 is a good balance between security and performance
        return new BCryptPasswordEncoder(10);
    }

}
