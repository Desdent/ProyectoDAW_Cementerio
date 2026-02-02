package com.imo.cemetery.service.cliente;

import com.imo.cemetery.model.dto.cliente.ClienteCreateDTO;
import com.imo.cemetery.model.dto.cliente.ClienteResponseDTO;
import com.imo.cemetery.model.dto.cliente.ClienteUpdateDTO;
import com.imo.cemetery.model.entity.Cliente;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The interface Cliente service.
 */
public interface ClienteService {

    /**
     * Create cliente response dto.
     *
     * @param dto the dto
     * @return the cliente response dto
     */
// CRUD
    ClienteResponseDTO create(ClienteCreateDTO dto);

    /**
     * Update cliente response dto.
     *
     * @param dto the dto
     * @param id  the id
     * @return the cliente response dto
     */
    ClienteResponseDTO update(ClienteUpdateDTO dto, Long id);

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
    List<ClienteResponseDTO> findAll();

    /**
     * Find by id cliente response dto.
     *
     * @param id the id
     * @return the cliente response dto
     */
    ClienteResponseDTO findById(Long id);

    /**
     * Find by dni cliente response dto.
     *
     * @param dni the dni
     * @return the cliente response dto
     */
    ClienteResponseDTO findByDni(String dni);

    /**
     * Find by email cliente response dto.
     *
     * @param email the email
     * @return the cliente response dto
     */
    ClienteResponseDTO findByEmail(String email);


    /**
     * Search list.
     *
     * @param term the term
     * @return the list
     */
// Búsquedas y Filtros
    List<ClienteResponseDTO> search(String term);

    /**
     * Find by ciudad id list.
     *
     * @param ciudadId the ciudad id
     * @return the list
     */
    List<ClienteResponseDTO> findByCiudadId(Long ciudadId);

    /**
     * Find by ciudad nombre list.
     *
     * @param ciudadNombre the ciudad nombre
     * @return the list
     */
    List<ClienteResponseDTO> findByCiudadNombre(String ciudadNombre);

    /**
     * Find by provincia id list.
     *
     * @param provinciaId the provincia id
     * @return the list
     */
    List<ClienteResponseDTO> findByProvinciaId(Long provinciaId);

    /**
     * Find by provincia nombre list.
     *
     * @param provinciaNombre the provincia nombre
     * @return the list
     */
    List<ClienteResponseDTO> findByProvinciaNombre(String provinciaNombre);

    /**
     * Find all by cementerio id list.
     *
     * @param id the id
     * @return the list
     */
    List<ClienteResponseDTO> findAllByCementerioId(Long id);

    /**
     * Find all by ayuntamiento id list.
     *
     * @param id the id
     * @return the list
     */
    List<ClienteResponseDTO> findAllByAyuntamientoId(Long id);

}
