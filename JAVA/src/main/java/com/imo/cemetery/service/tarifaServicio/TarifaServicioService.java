package com.imo.cemetery.service.tarifaServicio;

import com.imo.cemetery.model.dto.tarifaServicio.TarifaServicioCreateDTO;
import com.imo.cemetery.model.dto.tarifaServicio.TarifaServicioResponseDTO;
import com.imo.cemetery.model.dto.tarifaServicio.TarifaServicioUpdateDTO;
import com.imo.cemetery.model.enums.ServicioType;

import java.util.List;

public interface TarifaServicioService {
    TarifaServicioResponseDTO create(TarifaServicioCreateDTO dto);
    TarifaServicioResponseDTO update(Long id, TarifaServicioUpdateDTO dto);
    void deleteById(Long id);
    TarifaServicioResponseDTO findById(Long id);
    List<TarifaServicioResponseDTO> findAllByCementerio(Long cementerioId);
    TarifaServicioResponseDTO findPrecioServicio(Long cementerioId, ServicioType tipo);
}