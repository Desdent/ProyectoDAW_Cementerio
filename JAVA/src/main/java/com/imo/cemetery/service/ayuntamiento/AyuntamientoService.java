package com.imo.cemetery.service.ayuntamiento;

import com.imo.cemetery.model.dto.ayuntamiento.AyuntamientoCreateDTO;
import com.imo.cemetery.model.dto.ayuntamiento.AyuntamientoResponseDTO;
import com.imo.cemetery.model.dto.ayuntamiento.AyuntamientoUpdateDTO;
import com.imo.cemetery.model.entity.Ayuntamiento;

import java.util.List;
import java.util.Optional;

public interface AyuntamientoService {


    // CRUD
    AyuntamientoResponseDTO create(AyuntamientoCreateDTO dto);
    AyuntamientoResponseDTO update(AyuntamientoUpdateDTO dto, Long id);
    void deleteById(Long id);


    // Consultas
    AyuntamientoResponseDTO findById(Long id);
    AyuntamientoResponseDTO findByNif(String nif);
    List<AyuntamientoResponseDTO> findAll();
    AyuntamientoResponseDTO findByEmail(String email);
    boolean existsByEmail(String email);


    // Buscador y filtros
    List<AyuntamientoResponseDTO> findAllByProvinciaNombre(String nombre);
    List<AyuntamientoResponseDTO> findAllByProvinciaId(Long id);
    AyuntamientoResponseDTO findByCiudadNombre(String nombre);
    AyuntamientoResponseDTO findByCiudadId(Long id);
    List<AyuntamientoResponseDTO> findAllBySearchingTerm(String term); // La idea de este metodo es que se aplique cada vez que el usuario teclea algo en el buscador// Similar al de serachingTerm

}
