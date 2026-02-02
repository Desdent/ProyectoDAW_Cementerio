package com.imo.cemetery.service.cementerio;

import com.imo.cemetery.model.dto.cementerio.CementerioCreateDTO;
import com.imo.cemetery.model.dto.cementerio.CementerioResponseDTO;
import com.imo.cemetery.model.dto.cementerio.CementerioUpdateDTO;

import java.util.List;

/**
 * The interface Cementerio service.
 */
public interface CementerioService {

    /**
     * Create cementerio response dto.
     *
     * @param dto the dto
     * @return the cementerio response dto
     */
// CRUD
    CementerioResponseDTO create(CementerioCreateDTO dto);

    /**
     * Update cementerio response dto.
     *
     * @param dto the dto
     * @param id  the id
     * @return the cementerio response dto
     */
    CementerioResponseDTO update(CementerioUpdateDTO dto, Long id);

    /**
     * Delete by id.
     *
     * @param id the id
     */
    void deleteById(Long id);


    /**
     * Find all list.
     *
     * @return the list
     */
// Consultas
    List<CementerioResponseDTO> findAll();

    /**
     * Find by id cementerio response dto.
     *
     * @param id the id
     * @return the cementerio response dto
     */
    CementerioResponseDTO findById(Long id);

    /**
     * Find by email cementerio response dto.
     *
     * @param email the email
     * @return the cementerio response dto
     */
    CementerioResponseDTO findByEmail(String email);

    /**
     * Find all by logged ayuntamiento list.
     *
     * @return the list
     */
// Para que el ayuntamiento logueado vea su lista privada
    List<CementerioResponseDTO> findAllByLoggedAyuntamiento();

    /**
     * Find all by searching term list.
     *
     * @param term the term
     * @return the list
     */
// Buscador y filtros
    List<CementerioResponseDTO> findAllBySearchingTerm(String term);

    /**
     * Find all by provincia nombre list.
     *
     * @param nombre the nombre
     * @return the list
     */
    List<CementerioResponseDTO> findAllByProvinciaNombre(String nombre);

    /**
     * Find all by provincia id list.
     *
     * @param id the id
     * @return the list
     */
    List<CementerioResponseDTO> findAllByProvinciaId(Long id);

    /**
     * Find all by ciudad nombre list.
     *
     * @param nombre the nombre
     * @return the list
     */
    List<CementerioResponseDTO> findAllByCiudadNombre(String nombre);

    /**
     * Find all by ciudad id list.
     *
     * @param id the id
     * @return the list
     */
    List<CementerioResponseDTO> findAllByCiudadId(Long id);

    /**
     * Find all by ayuntamiento id list.
     *
     * @param id the id
     * @return the list
     */
    List<CementerioResponseDTO> findAllByAyuntamientoId(Long id);

    /**
     * Count by ayuntamiento id long.
     *
     * @param id the id
     * @return the long
     */
    Long countByAyuntamientoId(Long id);

    /**
     * Find all by ayuntamiento email list.
     *
     * @param email the email
     * @return the list
     */
    List<CementerioResponseDTO> findAllByAyuntamientoEmail(String email);

    /**
     * Find by concesion id cementerio response dto.
     *
     * @param id the id
     * @return the cementerio response dto
     */
    CementerioResponseDTO findByConcesionId(Long id);

}
