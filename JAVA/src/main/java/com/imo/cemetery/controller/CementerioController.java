package com.imo.cemetery.controller;


import com.imo.cemetery.model.dto.ayuntamiento.AyuntamientoResponseDTO;
import com.imo.cemetery.model.dto.cementerio.CementerioCreateDTO;
import com.imo.cemetery.model.dto.cementerio.CementerioResponseDTO;
import com.imo.cemetery.model.dto.cementerio.CementerioUpdateDTO;
import com.imo.cemetery.service.cementerio.CementerioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cementerios")
@RequiredArgsConstructor
@Slf4j // Para ver logs
@CrossOrigin
public class CementerioController {

    private final CementerioService service;


    // CRUD

    @PostMapping
    public ResponseEntity<CementerioResponseDTO> create(@Valid @RequestBody CementerioCreateDTO dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<CementerioResponseDTO>> findAll()
    {
        List<CementerioResponseDTO> response = service.findAll();
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CementerioResponseDTO> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<CementerioResponseDTO> findByEmail(@PathVariable String email)
    {
        return ResponseEntity.ok(service.findByEmail(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CementerioResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CementerioUpdateDTO dto)
    {
        return ResponseEntity.ok(service.update(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    // BÚSQUEDAS Y FILTROS

    @GetMapping("/search")
    public ResponseEntity<List<CementerioResponseDTO>> search(@RequestParam String term)
    {
        return ResponseEntity.ok(service.findAllBySearchingTerm(term));
    }

    @GetMapping("/filter-provincia/{id}")
    public ResponseEntity<List<CementerioResponseDTO>> filterByProvincia(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findAllByProvinciaId(id));
    }

    @GetMapping("/filter-ciudad/{id}")
    public ResponseEntity<List<CementerioResponseDTO>> filterByCiudad(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findAllByCiudadId(id));
    }

    @GetMapping("/filter-ayuntamiento/{id}")
    public ResponseEntity<List<CementerioResponseDTO>> filterByAyuntamientoId(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findAllByAyuntamientoId(id));
    }

    @GetMapping("/filter-ayuntamiento-email/{email}")
    public ResponseEntity<List<CementerioResponseDTO>> filterByAyuntamientoEmail(@PathVariable String email)
    {
        return ResponseEntity.ok(service.findAllByAyuntamientoEmail(email));
    }

    @GetMapping("/count/{aytoId}")
    public ResponseEntity<Long> countByAyuntamientoId(@PathVariable Long aytoId)
    {
        return ResponseEntity.ok(service.countByAyuntamientoId(aytoId));
    }

    @GetMapping("/my-cementerios")
    public ResponseEntity<List<CementerioResponseDTO>> myCementerios()
    {
        return ResponseEntity.ok(service.findAllByLoggedAyuntamiento());
    }
}
