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

/**
 * The type Facturacion controller.
 */
@RestController
@RequestMapping("/api/v1/facturaciones")
@RequiredArgsConstructor
@Slf4j

public class FacturacionController {

    private final FacturacionService service;


    // CRUD Y BÁSICOS

    /**
     * Create response entity.
     *
     * @param dto the dto
     * @return the response entity
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FacturacionResponseDTO> create(@Valid @RequestBody FacturacionCreateDTO dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<List<FacturacionResponseDTO>> findAll()
    {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Find by id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<FacturacionResponseDTO> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * Update response entity.
     *
     * @param id  the id
     * @param dto the dto
     * @return the response entity
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FacturacionResponseDTO> update(@PathVariable Long id, @Valid @RequestBody FacturacionUpdateDTO dto)
    {
        return ResponseEntity.ok(service.update(id, dto));
    }

    /**
     * Delete response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }


    // BÚSQUEDAS Y FILTROS

    /**
     * Search dni response entity.
     *
     * @param dni the dni
     * @return the response entity
     */
    @GetMapping("/search/dni")
    public ResponseEntity<List<FacturacionResponseDTO>> searchDni(@RequestParam (defaultValue = "") String dni)
    {
        return ResponseEntity.ok(service.findAllByDni(dni));
    }

    /**
     * Search pago response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/pagos/{id}/facturaciones")
    public ResponseEntity<FacturacionResponseDTO> searchPago(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findByPagoId(id));
    }


}
