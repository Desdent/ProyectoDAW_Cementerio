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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parcelas")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class ParcelaController {


    private final ParcelaService service;


    // CRUD y básicos

    @PostMapping
    public ResponseEntity<ParcelaResponseDTO> create(@RequestBody @Valid ParcelaCreateDTO dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ParcelaResponseDTO>> findAll()
    {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParcelaResponseDTO> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParcelaResponseDTO> update(@PathVariable Long id, @RequestBody ParcelaUpdateDTO dto)
    {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        return ResponseEntity.noContent().build();
    }


    // BÚSQUEDAS Y FILTROS

    @GetMapping("/zona/{id}")
    public ResponseEntity<List<ParcelaResponseDTO>> findAllByZonaId(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findAllByZonaId(id));
    }

    @GetMapping("/zona/{id}/libres")
    public ResponseEntity<List<ParcelaResponseDTO>> findAllLibresByZona(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findAllLibresByZona(id));
    }

    @GetMapping("/ubicacion")
    public ResponseEntity<ParcelaResponseDTO> findByUbicacion(@RequestParam double x,
                                                              @RequestParam double y,
                                                              @RequestParam int fila,
                                                              @RequestParam int columna)
    {
        return ResponseEntity.ok(service.findByUbicacionCompleta(x, y, fila, columna));
    }

}
