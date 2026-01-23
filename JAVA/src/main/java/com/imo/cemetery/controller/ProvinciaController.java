package com.imo.cemetery.controller;

import com.imo.cemetery.model.dto.provincia.ProvinciaResponseDTO;
import com.imo.cemetery.service.provincia.ProvinciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/provincias")
@RequiredArgsConstructor
public class ProvinciaController {

    private final ProvinciaService service;

    @GetMapping
    public ResponseEntity<List<ProvinciaResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
