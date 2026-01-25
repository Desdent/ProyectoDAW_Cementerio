package com.imo.cemetery.service.implementacionService;

import com.imo.cemetery.model.dto.implementacionServicio.ImplementacionServicioCreateDTO;
import com.imo.cemetery.model.dto.implementacionServicio.ImplementacionServicioResponseDTO;
import com.imo.cemetery.model.dto.implementacionServicio.ImplementacionServicioUpdateDTO;

import java.time.LocalDate;
import java.util.List;

public interface ImplementacionServicioService {
    ImplementacionServicioResponseDTO create(ImplementacionServicioCreateDTO dto);
    ImplementacionServicioResponseDTO update(Long id, ImplementacionServicioUpdateDTO dto);
    void deleteById(Long id);
    ImplementacionServicioResponseDTO findById(Long id);
    List<ImplementacionServicioResponseDTO> findAllByParcela(Long parcelaId);
    List<ImplementacionServicioResponseDTO> findAllByFechaRange(LocalDate inicio, LocalDate fin);
}
