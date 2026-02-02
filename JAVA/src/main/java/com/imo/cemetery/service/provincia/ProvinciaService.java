package com.imo.cemetery.service.provincia;

import com.imo.cemetery.model.dto.provincia.ProvinciaResponseDTO;
import com.imo.cemetery.model.entity.Provincia;

import java.util.List;

/**
 * The interface Provincia service.
 */
public interface ProvinciaService {

    /**
     * Gets all.
     *
     * @return the all
     */
    List<ProvinciaResponseDTO> getAll();

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    ProvinciaResponseDTO getById(Long id);

    /**
     * Create provincia response dto.
     *
     * @param nombre the nombre
     * @param id     the id
     * @return the provincia response dto
     */
    ProvinciaResponseDTO create(String nombre, Long id);

    /**
     * Gets entity by id.
     *
     * @param id the id
     * @return the entity by id
     */
    Provincia getEntityById(Long id);
}