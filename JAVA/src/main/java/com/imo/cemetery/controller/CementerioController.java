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

@RestController
@RequestMapping("/api/v1/cementerios")
@RequiredArgsConstructor
@Slf4j // Para ver logs
public class CementerioController {

    private final CementerioService service;


    // CRUD

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CementerioResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CementerioUpdateDTO dto)
    {
        return ResponseEntity.ok(service.update(dto, id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
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

    @GetMapping("/provincia/{id}")
    public ResponseEntity<List<CementerioResponseDTO>> filterByProvincia(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findAllByProvinciaId(id));
    }

    @GetMapping("/ciudad/{id}")
    public ResponseEntity<List<CementerioResponseDTO>> filterByCiudad(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findAllByCiudadId(id));
    }

    @GetMapping("/ayuntamiento/{id}")
    public ResponseEntity<List<CementerioResponseDTO>> filterByAyuntamientoId(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findAllByAyuntamientoId(id));
    }

    @GetMapping("/ayuntamiento/email/{email}")
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
    @PreAuthorize("hasRole('AYUNTAMIENTO')")
    public ResponseEntity<List<CementerioResponseDTO>> myCementerios()
    {
        return ResponseEntity.ok(service.findAllByLoggedAyuntamiento());
    }

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

            // 3. Generar nombre único: timestamp + nombre original
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = pathDir.resolve(fileName);

            // 4. Guardar archivo
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // 5. Devolver el nombre final para que Angular lo use en el JSON del cementerio
            return ResponseEntity.ok(Map.of("nombreArchivo", fileName));

        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error al guardar el archivo: " + e.getMessage()));
        }
    }

    @GetMapping("/concesion/{id}")
    public ResponseEntity<CementerioResponseDTO> findByConcesion(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findByConcesionId(id));
    }
}
