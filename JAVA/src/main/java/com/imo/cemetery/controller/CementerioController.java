package com.imo.cemetery.controller;

import com.imo.cemetery.model.dto.cementerio.CementerioCreateDTO;
import com.imo.cemetery.model.dto.cementerio.CementerioResponseDTO;
import com.imo.cemetery.model.mapper.CementerioMapper;
import com.imo.cemetery.repository.AyuntamientoRepository;
import com.imo.cemetery.service.cementerio.CementerioServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class CementerioController {

    private final CementerioServiceImpl service;
    private final AyuntamientoRepository ayuntamientoRepository;
    private final CementerioMapper cementerioMapper;

    @PostMapping
    public ResponseEntity<CementerioResponseDTO> create(@RequestBody @Valid CementerioCreateDTO dto) {
        // 1. Obtenemos el email de quien está logueado (desde el token JWT)
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // 2. Le pasamos el DTO y el EMAIL al servicio para que él haga el trabajo
        CementerioResponseDTO response = service.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Otros métodos
}
