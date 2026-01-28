package com.imo.cemetery.controller;

import com.imo.cemetery.model.dto.servicio.ServicioCreateDTO;
import com.imo.cemetery.model.dto.servicio.ServicioResponseDTO;
import com.imo.cemetery.model.dto.servicio.ServicioUpdateDTO;
import com.imo.cemetery.model.enums.ServicioType;
import com.imo.cemetery.service.servicio.ServicioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/servicios")
@RequiredArgsConstructor
@Slf4j
public class ServicioController {

    private final ServicioService service;


    // CRUD

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServicioResponseDTO> create (@Valid @RequestBody ServicioCreateDTO dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ServicioResponseDTO>> findAll()
    {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioResponseDTO> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServicioResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ServicioUpdateDTO dto)
    {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }


    // BÚSQUEDAS Y FILTROS

    @GetMapping("/tipo/{type}")
    public ResponseEntity<ServicioResponseDTO> findByTipo(@PathVariable ServicioType type)
    {
        return ResponseEntity.ok(service.findByTipo(type));
    }

}
