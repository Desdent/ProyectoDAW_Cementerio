package com.imo.cemetery.service.zona;

import com.imo.cemetery.model.dto.zona.ZonaCreateDTO;
import com.imo.cemetery.model.dto.zona.ZonaResponseDTO;
import com.imo.cemetery.model.dto.zona.ZonaUpdateDTO;

import java.util.List;

public interface ZonaService {
    ZonaResponseDTO create(ZonaCreateDTO dto);
    ZonaResponseDTO update(Long id, ZonaUpdateDTO dto);
    void deleteById(Long id);
    ZonaResponseDTO findById(Long id);
    List<ZonaResponseDTO> findAll();
    List<ZonaResponseDTO> findAllByCementerioId(Long cementerioId);
    List<ZonaResponseDTO> searchByNombre(String nombre);
    List<ZonaResponseDTO> findByNombreEnCementerio(String nombre, Long cementerioId);
}
