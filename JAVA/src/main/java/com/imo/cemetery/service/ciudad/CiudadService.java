package com.imo.cemetery.service.ciudad;


import com.imo.cemetery.model.dto.ciudad.CiudadResponseDTO;
import com.imo.cemetery.model.entity.Ciudad;

import java.util.List;

/**
 * The interface Ciudad service.
 */
public interface CiudadService {

    /**
     * Gets all.
     *
     * @return the all
     */
    List<CiudadResponseDTO> getAll();

    /**
     * Gets by provincia.
     *
     * @param provinciaId the provincia id
     * @return the by provincia
     */
    List<CiudadResponseDTO> getByProvincia(Long provinciaId);

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    CiudadResponseDTO getById(Long id);

    /**
     * Create ciudad response dto.
     *
     * @param nombre      the nombre
     * @param id          the id
     * @param provinciaId the provincia id
     * @return the ciudad response dto
     */
    CiudadResponseDTO create(String nombre, Long id, Long provinciaId);

}