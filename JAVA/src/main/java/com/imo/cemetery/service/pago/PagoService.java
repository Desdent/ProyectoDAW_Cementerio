package com.imo.cemetery.service.pago;

import com.imo.cemetery.model.dto.pago.PagoCreateDTO;
import com.imo.cemetery.model.dto.pago.PagoResponseDTO;
import com.imo.cemetery.model.dto.pago.PagoUpdateDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * The interface Pago service.
 */
public interface PagoService {
    /**
     * Create pago response dto.
     *
     * @param dto the dto
     * @return the pago response dto
     */
    PagoResponseDTO create(PagoCreateDTO dto);

    /**
     * Update pago response dto.
     *
     * @param id  the id
     * @param dto the dto
     * @return the pago response dto
     */
    PagoResponseDTO update(Long id, PagoUpdateDTO dto);

    /**
     * Delete by id.
     *
     * @param id the id
     */
    void deleteById(Long id);

    /**
     * Find by id pago response dto.
     *
     * @param id the id
     * @return the pago response dto
     */
    PagoResponseDTO findById(Long id);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<PagoResponseDTO> findAll();

    /**
     * Find all by fecha list.
     *
     * @param fecha the fecha
     * @return the list
     */
    List<PagoResponseDTO> findAllByFecha(LocalDate fecha);

    /**
     * Find all by cementerio list.
     *
     * @param cementerioId the cementerio id
     * @return the list
     */
    List<PagoResponseDTO> findAllByCementerio(Long cementerioId);
}
