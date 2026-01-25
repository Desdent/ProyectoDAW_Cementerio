package com.imo.cemetery.service.pago;

import com.imo.cemetery.model.dto.pago.PagoCreateDTO;
import com.imo.cemetery.model.dto.pago.PagoResponseDTO;
import com.imo.cemetery.model.dto.pago.PagoUpdateDTO;

import java.time.LocalDate;
import java.util.List;

public interface PagoService {
    PagoResponseDTO create(PagoCreateDTO dto);
    PagoResponseDTO update(Long id, PagoUpdateDTO dto);
    void deleteById(Long id);
    PagoResponseDTO findById(Long id);
    List<PagoResponseDTO> findAll();
    List<PagoResponseDTO> findAllByFecha(LocalDate fecha);
    List<PagoResponseDTO> findAllByCementerio(Long cementerioId);
}
