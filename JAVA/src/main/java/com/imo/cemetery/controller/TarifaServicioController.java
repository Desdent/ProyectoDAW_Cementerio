package com.imo.cemetery.controller;

import com.imo.cemetery.model.dto.tarifaServicio.TarifaServicioCreateDTO;
import com.imo.cemetery.model.dto.tarifaServicio.TarifaServicioResponseDTO;
import com.imo.cemetery.model.dto.tarifaServicio.TarifaServicioUpdateDTO;
import com.imo.cemetery.model.enums.ServicioType;
import com.imo.cemetery.service.tarifaServicio.TarifaServicioService;
import com.imo.cemetery.service.tarifaServicio.TarifaServicioServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Tarifa servicio controller.
 */
@RestController
@RequestMapping("/api/v1/tarifas-serv")
@RequiredArgsConstructor
@Slf4j
public class TarifaServicioController {

    private final TarifaServicioService service;


    // CRUD

    /**
     * Create response entity.
     *
     * @param dto the dto
     * @return the response entity
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TarifaServicioResponseDTO> create(@Valid @RequestBody TarifaServicioCreateDTO dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<List<TarifaServicioResponseDTO>> findAll()
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
    public ResponseEntity<TarifaServicioResponseDTO> findById(@PathVariable Long id)
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
    public ResponseEntity<TarifaServicioResponseDTO> update(@PathVariable Long id,
                                                            @Valid @RequestBody TarifaServicioUpdateDTO dto)
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
     * Find all by cementerio response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/cementerio/{id}")
    public ResponseEntity<List<TarifaServicioResponseDTO>> findAllByCementerio(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findAllByCementerio(id));
    }

    /**
     * Find precio servicio response entity.
     *
     * @param tipo the tipo
     * @param id   the id
     * @return the response entity
     */
    @GetMapping("/servicio/{tipo}/cementerio/{id}")
    public ResponseEntity<TarifaServicioResponseDTO> findPrecioServicio(@PathVariable ServicioType tipo,
                                                                        @PathVariable Long id)
    {
        return ResponseEntity.ok(service.findPrecioServicio(id, tipo));
    }

}
