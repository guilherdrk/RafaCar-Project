package com.rafacar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Usa a configuração de CORS definida na sua classe WebConfig
                .cors(withDefaults())

                // Desabilita a proteção CSRF, que não é necessária para APIs REST stateless
                .csrf(csrf -> csrf.disable())

                // Configura as regras de autorização
                .authorizeHttpRequests(authorize -> authorize
                        // Permite todas as requisições sem autenticação.
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
