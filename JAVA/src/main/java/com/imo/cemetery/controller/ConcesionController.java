package com.imo.cemetery.controller;

import com.imo.cemetery.model.dto.concesion.ConcesionCreateDTO;
import com.imo.cemetery.model.dto.concesion.ConcesionResponseDTO;
import com.imo.cemetery.model.dto.concesion.ConcesionUpdateDTO;
import com.imo.cemetery.service.concesion.ConcesionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/concesiones")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class ConcesionController {


    private final ConcesionService service;


    // CRUD y básicass

    @PostMapping
    public ResponseEntity<ConcesionResponseDTO> create(@RequestBody @Valid ConcesionCreateDTO dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ConcesionResponseDTO>> findAll()
    {
        List<ConcesionResponseDTO> response = service.findAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConcesionResponseDTO> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConcesionResponseDTO> update(@PathVariable Long id, @RequestBody ConcesionUpdateDTO dto)
    {
        return ResponseEntity.ok(service.update(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        return ResponseEntity.noContent().build();
    }


    // BÚSQUEDAS Y FILTROS

    @GetMapping("/parcela/{id}")
    public ResponseEntity<ConcesionResponseDTO> findByParcela(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findByParcelaId(id));
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<List<ConcesionResponseDTO>> findAllByCliente(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findAllByClienteId(id));
    }

    @GetMapping("/vencida")
    public ResponseEntity<List<ConcesionResponseDTO>> findAllByVencidas(@RequestParam String term)
    {
        List<ConcesionResponseDTO> response;

        if(term.equalsIgnoreCase("true"))
        {
            response = service.findAllByVencidaTrue();
        } else if (term.equalsIgnoreCase("false")) {
            response = service.findAllActivas();
        } else
        {
            response = null;
        }

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping("/fecha/before")
    public ResponseEntity<List<ConcesionResponseDTO>> findAllByFechaFinBefore(@RequestParam
                                                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                              LocalDate fecha)
    {
        log.info("Buscando concesiones antes de: {}", fecha);

        List<ConcesionResponseDTO> response = service.findAllByFechaFinBefore(fecha);

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping("/casi-vencidas")
    public ResponseEntity<List<ConcesionResponseDTO>> findAllCasiVencidas()
    {
        List<ConcesionResponseDTO> response = service.findAllCasiVencidas();

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping("/concesion/{id}")
    public ResponseEntity<ConcesionResponseDTO> findByPago(@PathVariable Long id)
    {
        ConcesionResponseDTO response = service.findByPagoId(id);

        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();

    }


}
