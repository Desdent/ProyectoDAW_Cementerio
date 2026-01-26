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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class ClienteController {

    private final ClienteService service;


    // CRUD

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> create(@Valid @RequestBody ClienteCreateDTO dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> findAll()
    {
        List<ClienteResponseDTO> response = service.findAll();
        return response.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(response);
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<ClienteResponseDTO> findByDni(@PathVariable String dni)
    {
        return ResponseEntity.ok(service.findByDni(dni));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ClienteUpdateDTO dto)
    {
        return ResponseEntity.ok(service.update(dto, id));
    }


    // BÚSQUEDAS Y FILTROS

    @GetMapping("/search")
    public ResponseEntity<List<ClienteResponseDTO>> search(@RequestParam String term)
    {
        return ResponseEntity.ok(service.search(term));
    }

    @GetMapping("/provincia/{id}")
    public ResponseEntity<List<ClienteResponseDTO>> findAllByProvincia(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findByProvinciaId(id));
    }

    @GetMapping("/ciudad/{id}")
    public ResponseEntity<List<ClienteResponseDTO>> findAllByCiudad(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findByCiudadId(id));
    }

}
