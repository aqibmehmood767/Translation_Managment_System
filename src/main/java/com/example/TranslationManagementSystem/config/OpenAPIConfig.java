package com.example.TranslationManagementSystem.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Translation Management API", version = "1.0", description = "API documentation"),
        security = {
                @SecurityRequirement(name = "bearerAuth") // must exactly match SecurityScheme.name
        }
)
@SecurityScheme(
        name = "bearerAuth", // must exactly match above
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenAPIConfig {
}

