package com.imo.cemetery.service.servicio;

import com.imo.cemetery.model.dto.servicio.ServicioCreateDTO;
import com.imo.cemetery.model.dto.servicio.ServicioResponseDTO;
import com.imo.cemetery.model.dto.servicio.ServicioUpdateDTO;
import com.imo.cemetery.model.enums.ServicioType;

import java.util.List;

/**
 * The interface Servicio service.
 */
public interface ServicioService {
    /**
     * Create servicio response dto.
     *
     * @param dto the dto
     * @return the servicio response dto
     */
    ServicioResponseDTO create(ServicioCreateDTO dto);

    /**
     * Update servicio response dto.
     *
     * @param id  the id
     * @param dto the dto
     * @return the servicio response dto
     */
    ServicioResponseDTO update(Long id, ServicioUpdateDTO dto);

    /**
     * Delete by id.
     *
     * @param id the id
     */
    void deleteById(Long id);

    /**
     * Find by id servicio response dto.
     *
     * @param id the id
     * @return the servicio response dto
     */
    ServicioResponseDTO findById(Long id);

    /**
     * Find by tipo servicio response dto.
     *
     * @param tipo the tipo
     * @return the servicio response dto
     */
    ServicioResponseDTO findByTipo(ServicioType tipo);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<ServicioResponseDTO> findAll();

    /**
     * Find all by ayuntamiento list.
     *
     * @param id the id
     * @return the list
     */
    List<ServicioResponseDTO> findAllByAyuntamiento(Long id);

    /**
     * Find all by cementerio list.
     *
     * @param id the id
     * @return the list
     */
    List<ServicioResponseDTO> findAllByCementerio(Long id);
}