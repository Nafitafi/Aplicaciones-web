package com.example.GameVault.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Spring Security solo aporta BCrypt; las rutas las protege nuestro AuthInterceptor.
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // CSRF desactivado para simplificar la práctica; en producción debe estar activo.
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(
                    frameOptions -> frameOptions.disable()))
            .authorizeHttpRequests(auth -> auth
                // permitAll: aquí no se bloquea nada, la validación de sesión vive en el interceptor.
                .anyRequest().permitAll()
            );

        return http.build();
    }

    // BCrypt: usa encode() al registrar y matches() al hacer login.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
