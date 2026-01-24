package com.imo.cemetery.service.cementerio;

import com.imo.cemetery.model.dto.cementerio.CementerioCreateDTO;
import com.imo.cemetery.model.dto.cementerio.CementerioResponseDTO;
import com.imo.cemetery.model.dto.cementerio.CementerioUpdateDTO;

import java.util.List;

public interface CementerioService {

    // CRUD
    CementerioResponseDTO create(CementerioCreateDTO dto);
    CementerioResponseDTO update(CementerioUpdateDTO dto, Long id);
    void deleteById(Long id);


    // Consultas
    List<CementerioResponseDTO> findAll();
    CementerioResponseDTO findById(Long id);
    CementerioResponseDTO findByEmail(String email);
    // Para que el ayuntamiento logueado vea su lista privada
    List<CementerioResponseDTO> findAllByLoggedAyuntamiento();

    // Buscador y filtros
    List<CementerioResponseDTO> findAllBySearchingTerm(String term);
    List<CementerioResponseDTO> findAllByProvinciaNombre(String nombre);
    List<CementerioResponseDTO> findAllByProvinciaId(Long id);
    List<CementerioResponseDTO> findAllByCiudadNombre(String nombre);
    List<CementerioResponseDTO> findALlByCiudadId(Long id);
    List<CementerioResponseDTO> findAllByAyuntamientoId(Long id);
    Long countByAyuntamientoId(Long id);
    List<CementerioResponseDTO> findAllByAyuntamientoEmail(String email);

}
