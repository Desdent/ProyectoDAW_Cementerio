package com.imo.cemetery.service.facturacion;

import com.imo.cemetery.model.dto.facturacion.FacturacionCreateDTO;
import com.imo.cemetery.model.dto.facturacion.FacturacionResponseDTO;
import com.imo.cemetery.model.dto.facturacion.FacturacionUpdateDTO;

import java.util.List;

public interface FacturacionService {
    FacturacionResponseDTO create(FacturacionCreateDTO dto);
    FacturacionResponseDTO update(Long id, FacturacionUpdateDTO dto);
    void deleteById(Long id);
    FacturacionResponseDTO findById(Long id);
    List<FacturacionResponseDTO> findAll();
    List<FacturacionResponseDTO> findAllByDni(String dni);
    FacturacionResponseDTO findByPagoId(Long pagoId);
}
