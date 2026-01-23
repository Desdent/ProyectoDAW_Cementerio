package com.imo.cemetery.controller;

import com.imo.cemetery.model.dto.ayuntamiento.AyuntamientoCreateDTO;
import com.imo.cemetery.model.dto.ayuntamiento.AyuntamientoResponseDTO;
import com.imo.cemetery.model.dto.ayuntamiento.AyuntamientoUpdateDTO;
import com.imo.cemetery.model.dto.cementerio.CementerioResponseDTO;
import com.imo.cemetery.model.mapper.AyuntamientoMapper;
import com.imo.cemetery.repository.AyuntamientoRepository;
import com.imo.cemetery.service.ayuntamiento.AyuntamientoServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Ayuntamiento controller.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("api/ayuntamientos")
@RequiredArgsConstructor
@Slf4j
public class AyuntamientoController {

    private final AyuntamientoServiceImpl service;
    private final PasswordEncoder passwordEncoder;
    private final AyuntamientoMapper mapper;


    @GetMapping("/get/{id}")
    public ResponseEntity<AyuntamientoResponseDTO> obtainById(@PathVariable Long id){

        return ResponseEntity.ok(service.findById(id)); // Esto hace el equivalente a lo de arriba
    }

    /**
     * Obtain all ayuntamientosResponseDTO entity.
     *
     * @return the response list
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<AyuntamientoResponseDTO>> obtainAllAyuntamientos(){
        // return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
        return ResponseEntity.ok(service.findAll()); // Esto hace el equivalente a lo de arriba
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerAyuntamiento(@RequestBody AyuntamientoCreateDTO ayuntamientoCreateDTO) {

        if (this.service.existsByEmail(ayuntamientoCreateDTO.getEmail())) {
            throw new RuntimeException("Email ocupado");
        }

        this.service.create(ayuntamientoCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Ayuntamiento registrado existosamente.");

    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<AyuntamientoResponseDTO> update(@PathVariable Long id, @RequestBody AyuntamientoUpdateDTO ayuntamientoUpdateDTO)
    {
        AyuntamientoResponseDTO updated = service.update(ayuntamientoUpdateDTO, id);
        // Si el service devuelve null, esto lanza una excepción
        if (updated == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id)
    {
        service.deleteById(id);

        return ResponseEntity.ok("Ayuntamiento borrado exitosamente.");
    }

}
