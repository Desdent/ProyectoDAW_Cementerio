package com.imo.cemetery.controller;

import com.imo.cemetery.model.dto.ciudad.CiudadResponseDTO;
import com.imo.cemetery.service.ciudad.CiudadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ciudades")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class CiudadController {

    private final CiudadService service;

    @GetMapping
    public ResponseEntity<List<CiudadResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CiudadResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/provincia/{provinciaId}")
    public ResponseEntity<List<CiudadResponseDTO>> getByProvincia(@PathVariable Long provinciaId) {
        return ResponseEntity.ok(service.getByProvincia(provinciaId));
    }
}
