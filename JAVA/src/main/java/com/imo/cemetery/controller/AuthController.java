package com.imo.cemetery.controller;

import com.imo.cemetery.model.entity.LoginRequest;
import com.imo.cemetery.model.entity.LoginResponse;
import com.imo.cemetery.model.entity.User;
import com.imo.cemetery.model.enums.RoleType;
import com.imo.cemetery.service.jwt.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        log.info("Datos recibidos en el API: {}", loginRequest);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        User user = (User) authentication.getPrincipal();
        String token = jwtService.generateToken(user);
        RoleType role = user.getRole().getTipo();
        String email = user.getEmail();

        return ResponseEntity.ok(new LoginResponse(token, role, email));
    }
}
