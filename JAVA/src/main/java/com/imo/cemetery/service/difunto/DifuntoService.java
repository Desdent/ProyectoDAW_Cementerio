package com.imo.cemetery.service.difunto;

import com.imo.cemetery.model.dto.difunto.DifuntoCreateDTO;
import com.imo.cemetery.model.dto.difunto.DifuntoResponseDTO;
import com.imo.cemetery.model.dto.difunto.DifuntoUpdateDTO;

import java.time.Year;
import java.util.List;

public interface DifuntoService {
    DifuntoResponseDTO create(DifuntoCreateDTO dto);
    DifuntoResponseDTO update(Long id, DifuntoUpdateDTO dto);
    void deleteById(Long id);
    DifuntoResponseDTO findById(Long id);
    List<DifuntoResponseDTO> findAll();
    List<DifuntoResponseDTO> findByNombreCompleto(String nombre, String ape1, String ape2);
    List<DifuntoResponseDTO> findAllByParcela(Long parcelaId);
    List<DifuntoResponseDTO> findAllByYearDefuncion(Year year);
}
