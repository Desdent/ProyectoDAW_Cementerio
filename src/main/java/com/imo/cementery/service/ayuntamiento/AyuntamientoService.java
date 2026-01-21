package com.imo.cementery.service.ayuntamiento;

import com.imo.cementery.model.dto.ayuntamiento.AyuntamientoCreateDTO;
import com.imo.cementery.model.dto.ayuntamiento.AyuntamientoResponseDTO;
import com.imo.cementery.model.dto.ayuntamiento.AyuntamientoUpdateDTO;

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

}
