package com.imo.cemetery.controller;

import com.imo.cemetery.model.dto.difunto.DifuntoCreateDTO;
import com.imo.cemetery.model.dto.difunto.DifuntoResponseDTO;
import com.imo.cemetery.model.dto.difunto.DifuntoUpdateDTO;
import com.imo.cemetery.service.difunto.DifuntoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/api/v1/difuntos")
@RequiredArgsConstructor
@Slf4j
public class DifuntoController {

    private final DifuntoService service;


    // CRUD y básicos

    @PostMapping
    public ResponseEntity<DifuntoResponseDTO> create(@RequestBody @Valid DifuntoCreateDTO dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<DifuntoResponseDTO>> findAll()
    {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DifuntoResponseDTO> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DifuntoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody DifuntoUpdateDTO dto)
    {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    // BÚSQUEDA Y FILTROS

    @GetMapping("/parcela/{id}")
    public ResponseEntity<List<DifuntoResponseDTO>> findByParcela(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findAllByParcela(id));
    }

    @GetMapping("/anno-defuncion/{year}")
    public ResponseEntity<List<DifuntoResponseDTO>> findAllByYearDefuncion(@PathVariable int year)
    {
        return ResponseEntity.ok(service.findAllByYearDefuncion(Year.of(year)));
    }

    @GetMapping("/fullname")
    public ResponseEntity<List<DifuntoResponseDTO>> findByName(@RequestParam String name,
                                                               @RequestParam (required = false, defaultValue = "") String ap1,
                                                               @RequestParam (required = false, defaultValue = "") String ap2)
    {
        return ResponseEntity.ok(service.findByFullName(name, ap1, ap2));
    }

}
