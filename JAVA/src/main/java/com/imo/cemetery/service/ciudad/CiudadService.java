package com.imo.cemetery.service.ciudad;


import com.imo.cemetery.model.dto.ciudad.CiudadResponseDTO;
import com.imo.cemetery.model.entity.Ciudad;

import java.util.List;

public interface CiudadService {

    List<CiudadResponseDTO> getAll();
    List<CiudadResponseDTO> getByProvincia(Long provinciaId);
    CiudadResponseDTO getById(Long id);

    CiudadResponseDTO create(String nombre, Long id, Long provinciaId);

}