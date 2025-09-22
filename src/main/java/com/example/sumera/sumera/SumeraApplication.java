package com.example.sumera.sumera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SumeraApplication {
	public static void main(String[] args) {
		SpringApplication.run(SumeraApplication.class, args);
		System.out.println("Startup Idea Validation Portal is running!");
		System.out.println("Swagger UI: http://localhost:8080/swagger-ui.html");
		System.out.println("API Docs: http://localhost:8080/api-docs");
	}
}
c