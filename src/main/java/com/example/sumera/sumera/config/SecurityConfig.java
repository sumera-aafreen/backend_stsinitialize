package com.example.sumera.sumera.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authz -> authz
                // Allow access to Swagger UI and API docs
                .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/api-docs/**", "/v3/api-docs/**").permitAll()
                // Allow access to all API endpoints for now (for testing)
                .requestMatchers("/api/**").permitAll()
                // Allow access to actuator endpoints
                .requestMatchers("/actuator/**").permitAll()
                // Allow access to static resources
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                // Allow all HTTP methods (GET, POST, PUT, DELETE, etc.)
                .anyRequest().permitAll()
            );
        
        return http.build();
    }
}
