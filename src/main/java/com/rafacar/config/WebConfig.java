package com.rafacar.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("[https://rafa-car-frontend.vercel.app](https://rafa-car-frontend.vercel.app)")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP que o frontend pode usar
                .allowedHeaders("*") // Permite que o frontend envie qualquer tipo de cabeçalho
                .allowCredentials(true); // Permite o envio de cookies ou tokens de autenticação
    }
}
