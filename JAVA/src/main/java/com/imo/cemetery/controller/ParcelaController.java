package com.imo.cemetery.controller;

import com.imo.cemetery.model.dto.parcela.ParcelaCreateDTO;
import com.imo.cemetery.model.dto.parcela.ParcelaResponseDTO;
import com.imo.cemetery.model.dto.parcela.ParcelaUpdateDTO;
import com.imo.cemetery.service.parcela.ParcelaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Parcela controller.
 */
@RestController
@RequestMapping("/api/v1/parcelas")
@RequiredArgsConstructor
@Slf4j
public class ParcelaController {


    private final ParcelaService service;


    // CRUD y básicos

    /**
     * Create response entity.
     *
     * @param dto the dto
     * @return the response entity
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AYUNTAMIENTO')")
    public ResponseEntity<ParcelaResponseDTO> create(@RequestBody @Valid ParcelaCreateDTO dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<List<ParcelaResponseDTO>> findAll()
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
    public ResponseEntity<ParcelaResponseDTO> findById(@PathVariable Long id)
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
    public ResponseEntity<ParcelaResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ParcelaUpdateDTO dto)
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
        return ResponseEntity.noContent().build();
    }


    // BÚSQUEDAS Y FILTROS

    /**
     * Find all by zona id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/zona/{id}")
    public ResponseEntity<List<ParcelaResponseDTO>> findAllByZonaId(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findAllByZonaId(id));
    }

    /**
     * Find all libres by zona response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/zona/{id}/libres")
    public ResponseEntity<List<ParcelaResponseDTO>> findAllLibresByZona(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findAllLibresByZona(id));
    }

    /**
     * Find by ubicacion response entity.
     *
     * @param x       the x
     * @param y       the y
     * @param fila    the fila
     * @param columna the columna
     * @return the response entity
     */
    @GetMapping("/ubicacion")
    public ResponseEntity<ParcelaResponseDTO> findByUbicacion(@RequestParam double x,
                                                              @RequestParam double y,
                                                              @RequestParam int fila,
                                                              @RequestParam int columna)
    {
        return ResponseEntity.ok(service.findByUbicacionCompleta(fila, columna));
    }

    /**
     * Find all by concesion response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/concesion/{id}")
    public ResponseEntity<List<ParcelaResponseDTO>> findAllByConcesion(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findAllByConcesion(id));
    }

}
