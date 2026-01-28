package com.imo.cemetery.controller;

import com.imo.cemetery.model.dto.implementacionServicio.ImplementacionServicioCreateDTO;
import com.imo.cemetery.model.dto.implementacionServicio.ImplementacionServicioResponseDTO;
import com.imo.cemetery.model.dto.implementacionServicio.ImplementacionServicioUpdateDTO;
import com.imo.cemetery.service.implementacionService.ImplementacionServicioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/impl-servicios")
@RequiredArgsConstructor
@Slf4j
public class ImplementacionServicioController {

    private final ImplementacionServicioService service;


    // CRUD

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ImplementacionServicioResponseDTO> create(@Valid @RequestBody ImplementacionServicioCreateDTO dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ImplementacionServicioResponseDTO>> findAll()
    {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImplementacionServicioResponseDTO> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ImplementacionServicioResponseDTO> update(@PathVariable Long id,
                                                                    @Valid @RequestBody ImplementacionServicioUpdateDTO dto)
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

    @GetMapping("/parcela/{id}")
    public ResponseEntity<List<ImplementacionServicioResponseDTO>> findAllByParcela(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findAllByParcela(id));
    }

    @GetMapping("/filter/fechas")
    public ResponseEntity<List<ImplementacionServicioResponseDTO>> findAllBetweenFechas(@RequestParam (required = true)
                                                                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                                            LocalDate fecha1,
                                                                                        @RequestParam (required = true)
                                                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                                                LocalDate fecha2)
    {
        return ResponseEntity.ok(service.findAllByFechaRange(fecha1, fecha2));
    }

}
