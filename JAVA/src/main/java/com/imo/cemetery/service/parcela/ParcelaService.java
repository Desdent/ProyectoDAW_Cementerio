package com.imo.cemetery.service.parcela;

import com.imo.cemetery.model.dto.parcela.ParcelaCreateDTO;
import com.imo.cemetery.model.dto.parcela.ParcelaResponseDTO;
import com.imo.cemetery.model.dto.parcela.ParcelaUpdateDTO;

import java.util.List;

/**
 * The interface Parcela service.
 */
public interface ParcelaService {

    /**
     * Create parcela response dto.
     *
     * @param dto the dto
     * @return the parcela response dto
     */
    ParcelaResponseDTO create(ParcelaCreateDTO dto);

    /**
     * Update parcela response dto.
     *
     * @param id  the id
     * @param dto the dto
     * @return the parcela response dto
     */
    ParcelaResponseDTO update(Long id, ParcelaUpdateDTO dto);

    /**
     * Delete by id.
     *
     * @param id the id
     */
    void deleteById(Long id);

    /**
     * Find by id parcela response dto.
     *
     * @param id the id
     * @return the parcela response dto
     */
    ParcelaResponseDTO findById(Long id);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<ParcelaResponseDTO> findAll();

    /**
     * Find all by zona id list.
     *
     * @param zonaId the zona id
     * @return the list
     */
    List<ParcelaResponseDTO> findAllByZonaId(Long zonaId);

    /**
     * Find all by cementerio id list.
     *
     * @param cementerioId the cementerio id
     * @return the list
     */
    List<ParcelaResponseDTO> findAllByCementerioId(Long cementerioId);

    /**
     * Find all libres by zona list.
     *
     * @param zonaId the zona id
     * @return the list
     */
    List<ParcelaResponseDTO> findAllLibresByZona(Long zonaId);

    /**
     * Find by ubicacion completa parcela response dto.
     *
     * @param fila    the fila
     * @param columna the columna
     * @return the parcela response dto
     */
    ParcelaResponseDTO findByUbicacionCompleta(Integer fila, Integer columna);

    /**
     * Find all by concesion list.
     *
     * @param id the id
     * @return the list
     */
    List<ParcelaResponseDTO> findAllByConcesion(Long id);

}