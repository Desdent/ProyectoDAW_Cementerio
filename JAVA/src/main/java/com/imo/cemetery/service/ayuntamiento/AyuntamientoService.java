package com.imo.cemetery.service.ayuntamiento;

import com.imo.cemetery.model.dto.ayuntamiento.AyuntamientoCreateDTO;
import com.imo.cemetery.model.dto.ayuntamiento.AyuntamientoResponseDTO;
import com.imo.cemetery.model.dto.ayuntamiento.AyuntamientoUpdateDTO;
import com.imo.cemetery.model.entity.Ayuntamiento;

import java.util.List;
import java.util.Optional;

/**
 * The interface Ayuntamiento service.
 */
public interface AyuntamientoService {


    /**
     * Create ayuntamiento response dto.
     *
     * @param dto the dto
     * @return the ayuntamiento response dto
     */
// CRUD
    AyuntamientoResponseDTO create(AyuntamientoCreateDTO dto);

    /**
     * Update ayuntamiento response dto.
     *
     * @param dto the dto
     * @param id  the id
     * @return the ayuntamiento response dto
     */
    AyuntamientoResponseDTO update(AyuntamientoUpdateDTO dto, Long id);

    /**
     * Delete by id.
     *
     * @param id the id
     */
    void deleteById(Long id);


    /**
     * Find by id ayuntamiento response dto.
     *
     * @param id the id
     * @return the ayuntamiento response dto
     */
// Consultas
    AyuntamientoResponseDTO findById(Long id);

    /**
     * Find by nif ayuntamiento response dto.
     *
     * @param nif the nif
     * @return the ayuntamiento response dto
     */
    AyuntamientoResponseDTO findByNif(String nif);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<AyuntamientoResponseDTO> findAll();

    /**
     * Find by email ayuntamiento response dto.
     *
     * @param email the email
     * @return the ayuntamiento response dto
     */
    AyuntamientoResponseDTO findByEmail(String email);

    /**
     * Exists by email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    boolean existsByEmail(String email);


    /**
     * Find all by provincia nombre list.
     *
     * @param nombre the nombre
     * @return the list
     */
// Buscador y filtros
    List<AyuntamientoResponseDTO> findAllByProvinciaNombre(String nombre);

    /**
     * Find all by provincia id list.
     *
     * @param id the id
     * @return the list
     */
    List<AyuntamientoResponseDTO> findAllByProvinciaId(Long id);

    /**
     * Find by ciudad nombre ayuntamiento response dto.
     *
     * @param nombre the nombre
     * @return the ayuntamiento response dto
     */
    AyuntamientoResponseDTO findByCiudadNombre(String nombre);

    /**
     * Find by ciudad id ayuntamiento response dto.
     *
     * @param id the id
     * @return the ayuntamiento response dto
     */
    AyuntamientoResponseDTO findByCiudadId(Long id);

    /**
     * Find all by searching term list.
     *
     * @param term the term
     * @return the list
     */
    List<AyuntamientoResponseDTO> findAllBySearchingTerm(String term); // La idea de este metodo es que se aplique cada vez que el usuario teclea algo en el buscador// Similar al de serachingTerm

}
