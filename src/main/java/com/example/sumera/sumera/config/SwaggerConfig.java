package com.example.sumera.sumera.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Startup Idea Validation Portal API")
                        .description("Comprehensive API for startup idea validation, market research, expert feedback, and analytics")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Sumera Development Team")
                                .email("support@sumera.com")
                                .url("https://sumera.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Development Server"),
                        new Server()
                                .url("https://api.sumera.com")
                                .description("Production Server")
                ));
    }
}

