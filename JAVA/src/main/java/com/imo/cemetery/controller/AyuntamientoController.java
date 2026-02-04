package com.imo.cemetery.controller;

import com.imo.cemetery.model.dto.ayuntamiento.AyuntamientoCreateDTO;
import com.imo.cemetery.model.dto.ayuntamiento.AyuntamientoResponseDTO;
import com.imo.cemetery.model.dto.ayuntamiento.AyuntamientoUpdateDTO;
import com.imo.cemetery.service.ayuntamiento.AyuntamientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Ayuntamiento controller.
 */
@RestController
@RequestMapping("/api/v1/ayuntamientos")
@RequiredArgsConstructor
@Slf4j
public class AyuntamientoController {

    private final AyuntamientoService service;
    // Se inyecta el servicio en lugar del service por desacoplamiento (poder cambiar el funcionamiento sin afectar al servicio)
                                    // e inversion de dependencias (modulos de alto nivel no se comunican con los de bajo nivel)

    // CRUD

    /**
     * Create response entity.
     *
     * @param dto the dto
     * @return the response entity
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AyuntamientoResponseDTO> create(@Valid @RequestBody AyuntamientoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<List<AyuntamientoResponseDTO>> findAll() {
        List<AyuntamientoResponseDTO> response = service.findAll();
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    /**
     * Find by id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AyuntamientoResponseDTO> findById(@PathVariable Long id) {
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
    @PreAuthorize("hasAnyRole('ADMIN', 'AYUNTAMIENTO')")
    public ResponseEntity<AyuntamientoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody AyuntamientoUpdateDTO dto) {
        return ResponseEntity.ok(service.update(dto, id));
    }

    /**
     * Delete response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.notFound().build();
    }

    // BÚSQUEDAS Y FILTROS

    /**
     * Search response entity.
     *
     * @param term the term
     * @return the response entity
     */
    @GetMapping("/search")
    public ResponseEntity<List<AyuntamientoResponseDTO>> search(@RequestParam String term) {
        return ResponseEntity.ok(service.findAllBySearchingTerm(term));
    }

    /**
     * Find by nif response entity.
     *
     * @param nif the nif
     * @return the response entity
     */
    @GetMapping("/nif/{nif}")
    public ResponseEntity<AyuntamientoResponseDTO> findByNif(@PathVariable String nif) {
        return ResponseEntity.ok(service.findByNif(nif));
    }

    /**
     * Find all by provincia response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/provincia/{id}")
    public ResponseEntity<List<AyuntamientoResponseDTO>> findAllByProvincia(@PathVariable Long id) {
        return ResponseEntity.ok(service.findAllByProvinciaId(id));
    }

    /**
     * Find by ciudad response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/ciudad/{id}")
    public ResponseEntity<AyuntamientoResponseDTO> findByCiudad(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByCiudadId(id));
    }
}