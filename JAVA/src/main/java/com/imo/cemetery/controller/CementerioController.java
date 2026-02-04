package com.imo.cemetery.controller;


import com.imo.cemetery.model.dto.ayuntamiento.AyuntamientoResponseDTO;
import com.imo.cemetery.model.dto.cementerio.CementerioCreateDTO;
import com.imo.cemetery.model.dto.cementerio.CementerioResponseDTO;
import com.imo.cemetery.model.dto.cementerio.CementerioUpdateDTO;
import com.imo.cemetery.service.cementerio.CementerioService;

import java.io.IOException;
import java.nio.file.Path;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

import java.util.List;

/**
 * The type Cementerio controller.
 */
@RestController
@RequestMapping("/api/v1/cementerios")
@RequiredArgsConstructor
@Slf4j // Para ver logs
public class CementerioController {

    private final CementerioService service;


    // CRUD

    /**
     * Create response entity.
     *
     * @param dto the dto
     * @return the response entity
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AYUNTAMIENTO')")
    public ResponseEntity<CementerioResponseDTO> create(@Valid @RequestBody CementerioCreateDTO dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<List<CementerioResponseDTO>> findAll()
    {
        List<CementerioResponseDTO> response = service.findAll();
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    /**
     * Find by id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<CementerioResponseDTO> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * Find by email response entity.
     *
     * @param email the email
     * @return the response entity
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<CementerioResponseDTO> findByEmail(@PathVariable String email)
    {
        return ResponseEntity.ok(service.findByEmail(email));
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
    public ResponseEntity<CementerioResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CementerioUpdateDTO dto)
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
    @PreAuthorize("hasAnyRole('ADMIN', 'AYUNTAMIENTO')")
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
    public ResponseEntity<List<CementerioResponseDTO>> search(@RequestParam String term)
    {
        return ResponseEntity.ok(service.findAllBySearchingTerm(term));
    }

    /**
     * Filter by provincia response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/provincia/{id}")
    public ResponseEntity<List<CementerioResponseDTO>> filterByProvincia(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findAllByProvinciaId(id));
    }

    /**
     * Filter by ciudad response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/ciudad/{id}")
    public ResponseEntity<List<CementerioResponseDTO>> filterByCiudad(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findAllByCiudadId(id));
    }

    /**
     * Filter by ayuntamiento id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/ayuntamiento/{id}")
    public ResponseEntity<List<CementerioResponseDTO>> filterByAyuntamientoId(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findAllByAyuntamientoId(id));
    }

    /**
     * Filter by ayuntamiento email response entity.
     *
     * @param email the email
     * @return the response entity
     */
    @GetMapping("/ayuntamiento/email/{email}")
    public ResponseEntity<List<CementerioResponseDTO>> filterByAyuntamientoEmail(@PathVariable String email)
    {
        return ResponseEntity.ok(service.findAllByAyuntamientoEmail(email));
    }

    /**
     * Count by ayuntamiento id response entity.
     *
     * @param aytoId the ayto id
     * @return the response entity
     */
    @GetMapping("/count/{aytoId}")
    public ResponseEntity<Long> countByAyuntamientoId(@PathVariable Long aytoId)
    {
        return ResponseEntity.ok(service.countByAyuntamientoId(aytoId));
    }

    /**
     * My cementerios response entity.
     *
     * @return the response entity
     */
    @GetMapping("/my-cementerios")
    @PreAuthorize("hasRole('AYUNTAMIENTO')")
    public ResponseEntity<List<CementerioResponseDTO>> myCementerios()
    {
        return ResponseEntity.ok(service.findAllByLoggedAyuntamiento());
    }

    /**
     * Upload map response entity.
     *
     * @param file the file
     * @return the response entity
     */
    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadMap(@RequestParam("archivo") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            // Define la ruta de la carpeta
            String uploadDir = "src/main/resources/static/uploads/mapas/";
            Path pathDir = Paths.get(uploadDir);

            // Crear carpetas si no existen
            if (!Files.exists(pathDir)) {
                Files.createDirectories(pathDir);
            }

            // Generar nombre único: timestamp + nombre original
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = pathDir.resolve(fileName);

            // Guardar archivo
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Devolver el nombre final para que Angular lo use en el JSON del cementerio
            return ResponseEntity.ok(Map.of("nombreArchivo", fileName));

        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error al guardar el archivo: " + e.getMessage()));
        }
    }

    /**
     * Find by concesion response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/concesion/{id}")
    public ResponseEntity<CementerioResponseDTO> findByConcesion(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findByConcesionId(id));
    }
}
