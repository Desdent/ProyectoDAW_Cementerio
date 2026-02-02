package com.imo.cemetery.controller;

import com.imo.cemetery.model.dto.provincia.ProvinciaResponseDTO;
import com.imo.cemetery.service.provincia.ProvinciaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Provincia controller.
 */
@RestController
@RequestMapping("/api/v1/provincias")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class ProvinciaController {

    private final ProvinciaService service;

    /**
     * Gets all.
     *
     * @return the all
     */
    @GetMapping
    public ResponseEntity<List<ProvinciaResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
