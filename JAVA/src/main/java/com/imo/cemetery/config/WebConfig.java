package com.imo.cemetery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/v1/**")
                .allowedOrigins("http://localhost:4200") // Origen
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos
                .allowedHeaders("*") // Permitir todas las cabeceras
                .allowCredentials(true) // Permitir envío de cookies o auth
                .maxAge(3600); // 1 hora de cache
    }
}