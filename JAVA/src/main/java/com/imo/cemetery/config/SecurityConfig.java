package com.imo.cemetery.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
// Anotación que indica que se inicia con la aplicación y permite crear un @Bean de tipo SecurityFilterChain
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        return http
                .csrf(csrf -> csrf.disable()) // Esta linea se encarga de quitar la proteccion contra Cross-Site Scripting
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/users/register").permitAll()
                        .requestMatchers("/api/ayuntamientos/register").permitAll()
                        .requestMatchers("/controller/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/controller/cemetery/**").permitAll()
                        .requestMatchers("/controller/cemetery/**").hasRole("ADMIN") // Ya le añade automaticamente el filtro el "ROLE_"
                        // En estas rutas es donde deberia estar el login y tal
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // ^^^^ en ese filtro se añaden (por ahora) que rutas son visibles de manera pública y cuáles no
                //.formLogin(login -> login
                //        .loginPage("/login")
                //       .permitAll()
                //);
                // ^^^^ en este filtro se añade un login personalizado a la ruta /login (hay que hacer un controller aure maneje esta petición GET igualmente), pero como por ahora no hay, usa el por defecto
                .build();


    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
