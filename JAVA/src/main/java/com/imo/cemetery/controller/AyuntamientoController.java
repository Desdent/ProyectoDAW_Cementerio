package com.imo.cemetery.controller;

import com.imo.cemetery.model.dto.ayuntamiento.AyuntamientoCreateDTO;
import com.imo.cemetery.model.mapper.AyuntamientoMapper;
import com.imo.cemetery.repository.AyuntamientoRepository;
import com.imo.cemetery.service.ayuntamiento.AyuntamientoServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AyuntamientoController {

    private final AyuntamientoRepository repo;
    private final AyuntamientoServiceImpl service;
    private final PasswordEncoder passwordEncoder;
    private final AyuntamientoMapper mapper;

    @PostMapping("/users/register")
    public ResponseEntity<String> registerAyuntamiento(@RequestBody AyuntamientoCreateDTO ayuntamientoCreateDTO) {

        if (this.service.existsByEmail(ayuntamientoCreateDTO.getEmail())) {
            throw new RuntimeException("Email ocupado");
        }

        this.service.create(ayuntamientoCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Ayuntamiento registrado existosamente");

    }

}
