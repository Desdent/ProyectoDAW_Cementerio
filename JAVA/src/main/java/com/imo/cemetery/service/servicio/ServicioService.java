package com.imo.cemetery.service.servicio;

import com.imo.cemetery.model.dto.servicio.ServicioCreateDTO;
import com.imo.cemetery.model.dto.servicio.ServicioResponseDTO;
import com.imo.cemetery.model.dto.servicio.ServicioUpdateDTO;
import com.imo.cemetery.model.enums.ServicioType;

import java.util.List;

public interface ServicioService {
    ServicioResponseDTO create(ServicioCreateDTO dto);
    ServicioResponseDTO update(Long id, ServicioUpdateDTO dto);
    void deleteById(Long id);
    ServicioResponseDTO findById(Long id);
    ServicioResponseDTO findByTipo(ServicioType tipo);
    List<ServicioResponseDTO> findAll();
}