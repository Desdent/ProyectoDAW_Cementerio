package com.imo.cemetery.controller;

import com.imo.cemetery.model.dto.cliente.ClienteCreateDTO;
import com.imo.cemetery.model.dto.cliente.ClienteResponseDTO;
import com.imo.cemetery.model.dto.cliente.ClienteUpdateDTO;
import com.imo.cemetery.service.cliente.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Cliente controller.
 */
@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
@Slf4j
public class ClienteController {

    private final ClienteService service;


    // CRUD

    /**
     * Create response entity.
     *
     * @param dto the dto
     * @return the response entity
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClienteResponseDTO> create(@Valid @RequestBody ClienteCreateDTO dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    /**
     * Find by id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> findAll()
    {
        List<ClienteResponseDTO> response = service.findAll();
        return response.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(response);
    }

    /**
     * Find by dni response entity.
     *
     * @param dni the dni
     * @return the response entity
     */
    @GetMapping("/dni/{dni}")
    public ResponseEntity<ClienteResponseDTO> findByDni(@PathVariable String dni)
    {
        return ResponseEntity.ok(service.findByDni(dni));
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
    public ResponseEntity<ClienteResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ClienteUpdateDTO dto)
    {
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
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    // BÚSQUEDAS Y FILTROS

    /**
     * Search response entity.
     *
     * @param term the term
     * @return the response entity
     */
    @GetMapping("/search")
    public ResponseEntity<List<ClienteResponseDTO>> search(@RequestParam String term)
    {
        return ResponseEntity.ok(service.search(term));
    }

    /**
     * Find all by provincia response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/provincia/{id}")
    public ResponseEntity<List<ClienteResponseDTO>> findAllByProvincia(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findByProvinciaId(id));
    }

    /**
     * Find all by ciudad response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/ciudad/{id}")
    public ResponseEntity<List<ClienteResponseDTO>> findAllByCiudad(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findByCiudadId(id));
    }

    /**
     * Find all by cementerio response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/cementerio/{id}")
    public ResponseEntity<List<ClienteResponseDTO>> findAllByCementerio(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findAllByCementerioId(id));
    }

    /**
     * Find all by ayuntamiento response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/ayuntamiento/{id}")
    public ResponseEntity<List<ClienteResponseDTO>> findAllByAyuntamiento(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findAllByAyuntamientoId(id));
    }



}
