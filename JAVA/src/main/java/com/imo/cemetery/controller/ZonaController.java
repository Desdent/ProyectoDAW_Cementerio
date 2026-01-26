package com.imo.cemetery.controller;


import com.imo.cemetery.model.dto.zona.ZonaCreateDTO;
import com.imo.cemetery.model.dto.zona.ZonaResponseDTO;
import com.imo.cemetery.model.dto.zona.ZonaUpdateDTO;
import com.imo.cemetery.service.zona.ZonaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/zonas")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class ZonaController {

    private final ZonaService service;


    // CRUD Y BÁSICOS

    @PostMapping
    public ResponseEntity<ZonaResponseDTO> create(@Valid  @RequestBody ZonaCreateDTO dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ZonaResponseDTO>> findAll()
    {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZonaResponseDTO> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ZonaResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ZonaUpdateDTO dto)
    {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }


    // BÚSQUEDAS Y FILTROS

    @GetMapping("/search")
    public ResponseEntity<List<ZonaResponseDTO>> searchByNombre(@RequestParam (defaultValue = "") String name)
    {
        return ResponseEntity.ok(service.searchByNombre(name));
    }

    @GetMapping("/search/cementerio/{id}")
    public ResponseEntity<List<ZonaResponseDTO>> searchByNombreInCementerio(@PathVariable Long id,
                                                                            @RequestParam (defaultValue = "") String name)
    {
        return ResponseEntity.ok(service.findByNombreEnCementerio(name, id));
    }

}
