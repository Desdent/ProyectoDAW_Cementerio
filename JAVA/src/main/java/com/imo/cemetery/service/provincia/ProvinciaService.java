package com.imo.cemetery.service.provincia;

import com.imo.cemetery.model.dto.provincia.ProvinciaResponseDTO;
import com.imo.cemetery.model.entity.Provincia;

import java.util.List;

public interface ProvinciaService {

    List<ProvinciaResponseDTO> getAll();
    ProvinciaResponseDTO getById(Long id);

    ProvinciaResponseDTO create(String nombre, Long id);

    Provincia getEntityById(Long id);
}