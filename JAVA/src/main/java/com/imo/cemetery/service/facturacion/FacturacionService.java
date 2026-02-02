package com.imo.cemetery.service.facturacion;

import com.imo.cemetery.model.dto.facturacion.FacturacionCreateDTO;
import com.imo.cemetery.model.dto.facturacion.FacturacionResponseDTO;
import com.imo.cemetery.model.dto.facturacion.FacturacionUpdateDTO;

import java.util.List;

/**
 * The interface Facturacion service.
 */
public interface FacturacionService {
    /**
     * Create facturacion response dto.
     *
     * @param dto the dto
     * @return the facturacion response dto
     */
    FacturacionResponseDTO create(FacturacionCreateDTO dto);

    /**
     * Update facturacion response dto.
     *
     * @param id  the id
     * @param dto the dto
     * @return the facturacion response dto
     */
    FacturacionResponseDTO update(Long id, FacturacionUpdateDTO dto);

    /**
     * Delete by id.
     *
     * @param id the id
     */
    void deleteById(Long id);

    /**
     * Find by id facturacion response dto.
     *
     * @param id the id
     * @return the facturacion response dto
     */
    FacturacionResponseDTO findById(Long id);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<FacturacionResponseDTO> findAll();

    /**
     * Find all by dni list.
     *
     * @param dni the dni
     * @return the list
     */
    List<FacturacionResponseDTO> findAllByDni(String dni);

    /**
     * Find by pago id facturacion response dto.
     *
     * @param pagoId the pago id
     * @return the facturacion response dto
     */
    FacturacionResponseDTO findByPagoId(Long pagoId);
}
