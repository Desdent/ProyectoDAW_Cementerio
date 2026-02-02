package com.imo.cemetery.service.concesion;

import com.imo.cemetery.model.dto.concesion.ConcesionCreateDTO;
import com.imo.cemetery.model.dto.concesion.ConcesionResponseDTO;
import com.imo.cemetery.model.dto.concesion.ConcesionUpdateDTO;
import com.imo.cemetery.model.dto.pago.PagoCreateDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * The interface Concesion service.
 */
public interface ConcesionService {

    /**
     * Create concesion response dto.
     *
     * @param dto     the dto
     * @param pagoDto the pago dto
     * @return the concesion response dto
     */
// CRUD
    ConcesionResponseDTO create(ConcesionCreateDTO dto, PagoCreateDTO pagoDto);

    /**
     * Update concesion response dto.
     *
     * @param dto the dto
     * @param id  the id
     * @return the concesion response dto
     */
    ConcesionResponseDTO update(ConcesionUpdateDTO dto, Long id);

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
    List<ConcesionResponseDTO> findAll();

    /**
     * Find by id concesion response dto.
     *
     * @param id the id
     * @return the concesion response dto
     */
    ConcesionResponseDTO findById(Long id);

    /**
     * Find by parcela id concesion response dto.
     *
     * @param id the id
     * @return the concesion response dto
     */
    ConcesionResponseDTO findByParcelaId(Long id);

    /**
     * Find all by cliente id list.
     *
     * @param id the id
     * @return the list
     */
    List<ConcesionResponseDTO> findAllByClienteId(Long id);

    /**
     * Find all by vencida true list.
     *
     * @return the list
     */
    List<ConcesionResponseDTO> findAllByVencidaTrue();

    /**
     * Find all activas list.
     *
     * @return the list
     */
    List<ConcesionResponseDTO> findAllActivas();

    /**
     * Find all by fecha fin before list.
     *
     * @param fecha the fecha
     * @return the list
     */
    List<ConcesionResponseDTO> findAllByFechaFinBefore(LocalDate fecha);

    /**
     * Find all by fecha fin between list.
     *
     * @param fecha1 the fecha 1
     * @param fecha2 the fecha 2
     * @return the list
     */
    List<ConcesionResponseDTO> findAllByFechaFinBetween(LocalDate fecha1, LocalDate fecha2);

    /**
     * Find all casi vencidas list.
     *
     * @return the list
     */
    List<ConcesionResponseDTO> findAllCasiVencidas(); // Un mes? Un año? de lejanía a la fechaFin

    /**
     * Find by pago id concesion response dto.
     *
     * @param id the id
     * @return the concesion response dto
     */
    ConcesionResponseDTO findByPagoId(Long id);

    /**
     * Find all by cementerio id list.
     *
     * @param id the id
     * @return the list
     */
    List<ConcesionResponseDTO> findAllByCementerioId(Long id);

    /**
     * Find all by cliente and ayuntamiento list.
     *
     * @param clienteId the cliente id
     * @param aytoId    the ayto id
     * @return the list
     */
    List<ConcesionResponseDTO> findAllByClienteAndAyuntamiento(Long clienteId, Long aytoId);

}
