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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ayuntamientos")
@RequiredArgsConstructor
@Slf4j
public class AyuntamientoController {

    private final AyuntamientoService service;
    // Se inyecta el servicio en lugar del service por desacoplamiento (poder cambiar el funcionamiento sin afectar al servicio)
                                    // e inversion de dependencias (modulos de alto nivel no se comunican con los de bajo nivel)

    // CRUD

    @PostMapping
    public ResponseEntity<AyuntamientoResponseDTO> create(@Valid @RequestBody AyuntamientoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<AyuntamientoResponseDTO>> findAll() {
        List<AyuntamientoResponseDTO> response = service.findAll();
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AyuntamientoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AyuntamientoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody AyuntamientoUpdateDTO dto) {
        return ResponseEntity.ok(service.update(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.notFound().build();
    }

    // BÚSQUEDAS Y FILTROS

    @GetMapping("/search")
    public ResponseEntity<List<AyuntamientoResponseDTO>> search(@RequestParam String term) {
        return ResponseEntity.ok(service.findAllBySearchingTerm(term));
    }

    @GetMapping("/nif/{nif}")
    public ResponseEntity<AyuntamientoResponseDTO> findByNif(@PathVariable String nif) {
        return ResponseEntity.ok(service.findByNif(nif));
    }

    @GetMapping("/provincia/{id}")
    public ResponseEntity<List<AyuntamientoResponseDTO>> findAllByProvincia(@PathVariable Long id) {
        return ResponseEntity.ok(service.findAllByProvinciaId(id));
    }

    @GetMapping("/ciudad/{id}")
    public ResponseEntity<AyuntamientoResponseDTO> findByCiudad(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByCiudadId(id));
    }
}