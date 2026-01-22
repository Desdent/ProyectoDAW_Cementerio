package com.imo.cemetery.service.cementerio;

import com.imo.cemetery.model.dto.cementerio.CementerioCreateDTO;
import com.imo.cemetery.model.dto.cementerio.CementerioResponseDTO;
import com.imo.cemetery.model.dto.cementerio.CementerioUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface CementerioService {

    List<CementerioResponseDTO> findAll();

    Optional<CementerioResponseDTO> findById(Long id);

    CementerioResponseDTO create(CementerioCreateDTO dto);

    void deleteById(Long id);

    default CementerioResponseDTO update(CementerioUpdateDTO dto, Long id) {
        return null;
    }
    

    Optional<CementerioResponseDTO> save(CementerioCreateDTO cementerio);

}
