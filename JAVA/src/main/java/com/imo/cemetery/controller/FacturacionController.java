package com.imo.cemetery.controller;

import com.imo.cemetery.model.dto.facturacion.FacturacionCreateDTO;
import com.imo.cemetery.model.dto.facturacion.FacturacionResponseDTO;
import com.imo.cemetery.model.dto.facturacion.FacturacionUpdateDTO;
import com.imo.cemetery.service.facturacion.FacturacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/facturaciones")
@RequiredArgsConstructor
@Slf4j

public class FacturacionController {

    private final FacturacionService service;


    // CRUD Y BÁSICOS

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FacturacionResponseDTO> create(@Valid @RequestBody FacturacionCreateDTO dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<FacturacionResponseDTO>> findAll()
    {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturacionResponseDTO> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FacturacionResponseDTO> update(@PathVariable Long id, @Valid @RequestBody FacturacionUpdateDTO dto)
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

    @GetMapping("/search/dni")
    public ResponseEntity<List<FacturacionResponseDTO>> searchDni(@RequestParam (defaultValue = "") String dni)
    {
        return ResponseEntity.ok(service.findAllByDni(dni));
    }

    @GetMapping("/pagos/{id}/facturaciones")
    public ResponseEntity<FacturacionResponseDTO> searchPago(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findByPagoId(id));
    }


}
