package com.imo.cemetery.service.ayuntamiento;

import com.imo.cemetery.model.dto.ayuntamiento.AyuntamientoCreateDTO;
import com.imo.cemetery.model.dto.ayuntamiento.AyuntamientoResponseDTO;
import com.imo.cemetery.model.dto.ayuntamiento.AyuntamientoUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface AyuntamientoService {

    List<AyuntamientoResponseDTO> findAll();

    Optional<AyuntamientoResponseDTO> findById(Long id);

    AyuntamientoResponseDTO create(AyuntamientoCreateDTO dto);

    void deleteById(Long id);

    default AyuntamientoResponseDTO update(AyuntamientoUpdateDTO dto, Long id) {
        return null;
    }

    boolean existsByEmail(String email);

}
