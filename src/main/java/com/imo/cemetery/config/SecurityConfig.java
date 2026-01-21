package com.imo.cemetery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// Anotación que indica que se inicia con la aplicación y permite crear un @Bean de tipo SecurityFilterChain
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Esta linea se encarga de quitar la proteccion contra Cross-Site Scripting
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/users").permitAll()
                        .anyRequest().authenticated()
                )
                // ^^^^ en ese filtro se añaden (por ahora) que rutas son visibles de manera pública y cuáles no
                //.formLogin(login -> login
                //        .loginPage("/login")
                //       .permitAll()
                //);
                // ^^^^ en este filtro se añade un login personalizado a la ruta /login (hay que hacer un controller aure maneje esta petición GET igualmente), pero como por ahora no hay, usa el por defecto
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
