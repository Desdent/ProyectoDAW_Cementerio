package com.imo.cemetery.service.tarifaServicio;

import com.imo.cemetery.model.dto.tarifaServicio.TarifaServicioCreateDTO;
import com.imo.cemetery.model.dto.tarifaServicio.TarifaServicioResponseDTO;
import com.imo.cemetery.model.dto.tarifaServicio.TarifaServicioUpdateDTO;
import com.imo.cemetery.model.enums.ServicioType;

import java.util.List;

/**
 * The interface Tarifa servicio service.
 */
public interface TarifaServicioService {
    /**
     * Create tarifa servicio response dto.
     *
     * @param dto the dto
     * @return the tarifa servicio response dto
     */
    TarifaServicioResponseDTO create(TarifaServicioCreateDTO dto);

    /**
     * Update tarifa servicio response dto.
     *
     * @param id  the id
     * @param dto the dto
     * @return the tarifa servicio response dto
     */
    TarifaServicioResponseDTO update(Long id, TarifaServicioUpdateDTO dto);

    /**
     * Delete by id.
     *
     * @param id the id
     */
    void deleteById(Long id);

    /**
     * Find by id tarifa servicio response dto.
     *
     * @param id the id
     * @return the tarifa servicio response dto
     */
    TarifaServicioResponseDTO findById(Long id);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<TarifaServicioResponseDTO> findAll();

    /**
     * Find all by cementerio list.
     *
     * @param cementerioId the cementerio id
     * @return the list
     */
    List<TarifaServicioResponseDTO> findAllByCementerio(Long cementerioId);

    /**
     * Find precio servicio tarifa servicio response dto.
     *
     * @param cementerioId the cementerio id
     * @param tipo         the tipo
     * @return the tarifa servicio response dto
     */
    TarifaServicioResponseDTO findPrecioServicio(Long cementerioId, ServicioType tipo);
}