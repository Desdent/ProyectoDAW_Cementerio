package com.imo.cemetery.controller;

import com.imo.cemetery.model.dto.concesion.ConcesionCreateDTO;
import com.imo.cemetery.model.dto.concesion.ConcesionResponseDTO;
import com.imo.cemetery.model.dto.concesion.ConcesionUpdateDTO;
import com.imo.cemetery.model.dto.pago.PagoCreateDTO;
import com.imo.cemetery.service.concesion.ConcesionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * The type Concesion controller.
 */
@RestController
@RequestMapping("/api/v1/concesiones")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class ConcesionController {


    private final ConcesionService service;


    // CRUD y básicass

    /**
     * The type Compra request.
     */
// Sin esto no me funciona el create, aparentemente no se pueden usar dos requestbody
    // Si da tiempo sacarlo y meterlo en dtos: prioridad Baja
    public record CompraRequest(
            @Valid ConcesionCreateDTO concesion,
            @Valid PagoCreateDTO pago
    ) {}

    /**
     * Create response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<ConcesionResponseDTO> create(@RequestBody @Valid CompraRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(request.concesion(), request.pago()));
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<List<ConcesionResponseDTO>> findAll()
    {
        List<ConcesionResponseDTO> response = service.findAll();

        return ResponseEntity.ok(response);
    }

    /**
     * Find by id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<ConcesionResponseDTO> findById(@PathVariable Long id)
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
    public ResponseEntity<ConcesionResponseDTO> update(@PathVariable Long id, @RequestBody ConcesionUpdateDTO dto)
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
     * Find by parcela response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/parcela/{id}")
    public ResponseEntity<ConcesionResponseDTO> findByParcela(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findByParcelaId(id));
    }

    /**
     * Find all by cliente response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/cliente/{id}")
    public ResponseEntity<List<ConcesionResponseDTO>> findAllByCliente(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findAllByClienteId(id));
    }

    /**
     * Find all by vencidas response entity.
     *
     * @param term the term
     * @return the response entity
     */
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

    /**
     * Find all by fecha fin before response entity.
     *
     * @param fecha the fecha
     * @return the response entity
     */
    @GetMapping("/fecha/before")
    public ResponseEntity<List<ConcesionResponseDTO>> findAllByFechaFinBefore(@RequestParam
                                                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                              LocalDate fecha)
    {
        log.info("Buscando concesiones antes de: {}", fecha);

        List<ConcesionResponseDTO> response = service.findAllByFechaFinBefore(fecha);

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    /**
     * Find all casi vencidas response entity.
     *
     * @return the response entity
     */
    @GetMapping("/casi-vencidas")
    public ResponseEntity<List<ConcesionResponseDTO>> findAllCasiVencidas()
    {
        List<ConcesionResponseDTO> response = service.findAllCasiVencidas();

        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    /**
     * Find by pago response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/concesion/{id}")
    public ResponseEntity<ConcesionResponseDTO> findByPago(@PathVariable Long id)
    {
        ConcesionResponseDTO response = service.findByPagoId(id);

        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();

    }

    /**
     * Gets concesiones by cliente and ayto.
     *
     * @param clienteId the cliente id
     * @param aytoId    the ayto id
     * @return the concesiones by cliente and ayto
     */
    @GetMapping("/cliente/{clienteId}/ayuntamiento/{aytoId}")
    public ResponseEntity<List<ConcesionResponseDTO>> getConcesionesByClienteAndAyto(
            @PathVariable Long clienteId,
            @PathVariable Long aytoId) {
        return ResponseEntity.ok(service.findAllByClienteAndAyuntamiento(clienteId, aytoId));
    }




}
