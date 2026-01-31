package com.imo.cemetery.controller;

import com.imo.cemetery.model.dto.cementerio.CementerioResponseDTO;
import com.imo.cemetery.model.dto.difunto.DifuntoCreateDTO;
import com.imo.cemetery.model.dto.difunto.DifuntoResponseDTO;
import com.imo.cemetery.model.dto.difunto.DifuntoUpdateDTO;
import com.imo.cemetery.model.entity.Cementerio;
import com.imo.cemetery.model.entity.User;
import com.imo.cemetery.service.ayuntamiento.AyuntamientoService;
import com.imo.cemetery.service.cementerio.CementerioService;
import com.imo.cemetery.service.difunto.DifuntoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/api/v1/difuntos")
@RequiredArgsConstructor
@Slf4j
public class DifuntoController {

    private final DifuntoService service;
    private final CementerioService cementerioService;
    private final AyuntamientoService ayuntamientoService;


    // CRUD y básicos

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') || hasRole('AYUNTAMIENTO')")
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
    @PreAuthorize("hasRole('ADMIN') || hasRole('AYUNTAMIENTO')")
    public ResponseEntity<DifuntoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody DifuntoUpdateDTO dto)
    {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('AYUNTAMIENTO')")
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

    @GetMapping("/ayuntamiento/{id}")
    public ResponseEntity<List<DifuntoResponseDTO>> findAllByAyuntamientoId(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findAllByAyuntamientoId(id));
    }

    @GetMapping("/cementerio/{id}")
    public ResponseEntity<List<DifuntoResponseDTO>> findAllByCementerioId(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.findAllByCementerioId(id));
    }

    /* Al final estos métodos no hacen falta porque cualquiera deberia poder consultar que muertos hay en un cementerio
    pero mira que guapos estan manolo me lo estaba currando


     @GetMapping("/ayuntamiento/{id}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('AYUNTAMIENTO')")
    public ResponseEntity<List<DifuntoResponseDTO>> findAllByAyuntamientoId(@PathVariable Long id)
    {
        User user = (User) auth.getPrincipal();
        ResponseEntity<List<DifuntoResponseDTO>> response;
        if(user.getRole().equals("AYUNTAMIENTO") || user.getRole().equals("ROLE_AYUNTAMIENTO"))
        {
            Long userId = user.getId();

            if(userId.equals(id))
            {
                response = ResponseEntity.ok(service.findAllByAyuntamientoId(id));
            }
            else
            {
                response = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }


        }
        else
        {
            response = ResponseEntity.ok(service.findAllByAyuntamientoId(id));
        }

        return response;

    }

    @GetMapping("/ayuntamiento/{id}/cementerio/{id}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('AYUNTAMIENTO')")
    public ResponseEntity<List<DifuntoResponseDTO>> findAllByCementerioId(@PathVariable Long idAyu,
                                                                          @PathVariable Long idCem)
    {
        User user = (User) auth.getPrincipal();
        ResponseEntity<List<DifuntoResponseDTO>> response;
        if(user.getRole().equals("AYUNTAMIENTO") || user.getRole().equals("ROLE_AYUNTAMIENTO"))
        {
            Long userId = user.getId();

            if(userId.equals(idAyu))
            {
                List<CementerioResponseDTO> cementerios = cementerioService.findAllByLoggedAyuntamiento();
                if(cementerios.stream().anyMatch(c -> c.getId().equals(idCem)))
                {
                    response = ResponseEntity.ok(service.findAllByCementerioId(idCem));
                }
            }
            else
            {
                response = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }


        }
        else
        {
            response = ResponseEntity.ok(service.findAllByCementerioId(idCem));
        }

        return response;

    }
     */

}
