package com.imo.cemetery.service.parcela;

import com.imo.cemetery.model.dto.parcela.ParcelaCreateDTO;
import com.imo.cemetery.model.dto.parcela.ParcelaResponseDTO;
import com.imo.cemetery.model.dto.parcela.ParcelaUpdateDTO;

import java.util.List;

public interface ParcelaService {

    ParcelaResponseDTO create(ParcelaCreateDTO dto);

    ParcelaResponseDTO update(Long id, ParcelaUpdateDTO dto);

    void deleteById(Long id);

    ParcelaResponseDTO findById(Long id);

    List<ParcelaResponseDTO> findAll();

    List<ParcelaResponseDTO> findAllByZonaId(Long zonaId);

    List<ParcelaResponseDTO> findAllByCementerioId(Long cementerioId);

    List<ParcelaResponseDTO> findAllLibresByZona(Long zonaId);

    ParcelaResponseDTO findByUbicacionCompleta(Double x, Double y, Integer fila, Integer columna);



}