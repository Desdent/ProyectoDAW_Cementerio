package com.imo.cemetery.controller;

import com.imo.cemetery.model.dto.facturacion.FacturacionResponseDTO;
import com.imo.cemetery.model.dto.pago.PagoCreateDTO;
import com.imo.cemetery.model.dto.pago.PagoResponseDTO;
import com.imo.cemetery.model.dto.pago.PagoUpdateDTO;
import com.imo.cemetery.service.pago.PagoService;
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

/**
 * The type Pago controller.
 */
@RestController
@RequestMapping("/api/v1/pagos")
@RequiredArgsConstructor
@Slf4j
public class PagoController {

    private final PagoService service;


    // CRUD Y BÁSICOS

    /**
     * Create response entity.
     *
     * @param dto the dto
     * @return the response entity
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PagoResponseDTO> create(@Valid @RequestBody PagoCreateDTO dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>> findAll()
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
    public ResponseEntity<PagoResponseDTO> findById(@PathVariable Long id)
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
    public ResponseEntity<PagoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody PagoUpdateDTO dto)
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
     * Search response entity.
     *
     * @param fecha the fecha
     * @return the response entity
     */
    @GetMapping("/search/fecha")
    public ResponseEntity<List<PagoResponseDTO>> search(@RequestParam
                                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                  LocalDate fecha)
    {
        return ResponseEntity.ok(service.findAllByFecha(fecha));
    }

    /**
     * Search all by cementerio response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/search/cementerio/{id}")
    public ResponseEntity<List<PagoResponseDTO>> searchAllByCementerio(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findAllByCementerio(id));
    }

}
