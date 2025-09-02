package com.rafacar.config;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Aplica a configuração de CORS definida abaixo
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Desabilita a proteção CSRF, que não é necessária para APIs REST stateless
                .csrf(csrf -> csrf.disable())

                // Configura as regras de autorização
                .authorizeHttpRequests(authorize -> authorize
                        // Permite todas as requisições sem autenticação.
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Permite requisições de QUALQUER origem
        configuration.setAllowedOrigins(Arrays.asList("*"));
        // Permite todos os métodos HTTP (GET, POST, PUT, DELETE, OPTIONS, etc.)
        configuration.setAllowedMethods(Arrays.asList("*"));
        // Permite todos os cabeçalhos
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
