package com.imo.cemetery.service.concesion;

import com.imo.cemetery.model.dto.concesion.ConcesionCreateDTO;
import com.imo.cemetery.model.dto.concesion.ConcesionResponseDTO;
import com.imo.cemetery.model.dto.concesion.ConcesionUpdateDTO;

import java.time.LocalDate;
import java.util.List;

public interface ConcesionService {

    // CRUD
    ConcesionResponseDTO create(ConcesionCreateDTO dto);
    ConcesionResponseDTO update(ConcesionUpdateDTO dto, Long id);
    void deleteById(Long id);


    // Consultas
    List<ConcesionResponseDTO> findAllByParcelaId(Long id);
    List<ConcesionResponseDTO> findAllByClienteId(Long id);
    List<ConcesionResponseDTO> findAllByVencidaTrue();
    List<ConcesionResponseDTO> findAllActivas();
    List<ConcesionResponseDTO> findAllByFechaFinBefore(LocalDate fecha);
    List<ConcesionResponseDTO> findAllByFechaFinBetween(LocalDate fecha1, LocalDate fecha2);
    List<ConcesionResponseDTO> findAllCasiVencidas(); // Un mes? Un año? de lejanía a la fechaFin
    ConcesionResponseDTO findAllByPagoId(Long id);
    List<ConcesionResponseDTO> findAllByCementerioId(Long id);

}
