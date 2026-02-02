package com.imo.cemetery.service.implementacionService;

import com.imo.cemetery.model.dto.implementacionServicio.ImplementacionServicioCreateDTO;
import com.imo.cemetery.model.dto.implementacionServicio.ImplementacionServicioResponseDTO;
import com.imo.cemetery.model.dto.implementacionServicio.ImplementacionServicioUpdateDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * The interface Implementacion servicio service.
 */
public interface ImplementacionServicioService {
    /**
     * Create implementacion servicio response dto.
     *
     * @param dto the dto
     * @return the implementacion servicio response dto
     */
    ImplementacionServicioResponseDTO create(ImplementacionServicioCreateDTO dto);

    /**
     * Update implementacion servicio response dto.
     *
     * @param id  the id
     * @param dto the dto
     * @return the implementacion servicio response dto
     */
    ImplementacionServicioResponseDTO update(Long id, ImplementacionServicioUpdateDTO dto);

    /**
     * Delete by id.
     *
     * @param id the id
     */
    void deleteById(Long id);

    /**
     * Find by id implementacion servicio response dto.
     *
     * @param id the id
     * @return the implementacion servicio response dto
     */
    ImplementacionServicioResponseDTO findById(Long id);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<ImplementacionServicioResponseDTO> findAll();

    /**
     * Find all by parcela list.
     *
     * @param parcelaId the parcela id
     * @return the list
     */
    List<ImplementacionServicioResponseDTO> findAllByParcela(Long parcelaId);

    /**
     * Find all by fecha range list.
     *
     * @param inicio the inicio
     * @param fin    the fin
     * @return the list
     */
    List<ImplementacionServicioResponseDTO> findAllByFechaRange(LocalDate inicio, LocalDate fin);
}
