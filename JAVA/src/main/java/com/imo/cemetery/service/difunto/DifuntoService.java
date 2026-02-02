package com.imo.cemetery.service.difunto;

import com.imo.cemetery.model.dto.difunto.DifuntoCreateDTO;
import com.imo.cemetery.model.dto.difunto.DifuntoResponseDTO;
import com.imo.cemetery.model.dto.difunto.DifuntoUpdateDTO;

import java.time.Year;
import java.util.List;

/**
 * The interface Difunto service.
 */
public interface DifuntoService {
    /**
     * Create difunto response dto.
     *
     * @param dto the dto
     * @return the difunto response dto
     */
    DifuntoResponseDTO create(DifuntoCreateDTO dto);

    /**
     * Update difunto response dto.
     *
     * @param id  the id
     * @param dto the dto
     * @return the difunto response dto
     */
    DifuntoResponseDTO update(Long id, DifuntoUpdateDTO dto);

    /**
     * Delete by id.
     *
     * @param id the id
     */
    void deleteById(Long id);

    /**
     * Find by id difunto response dto.
     *
     * @param id the id
     * @return the difunto response dto
     */
    DifuntoResponseDTO findById(Long id);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<DifuntoResponseDTO> findAll();

    /**
     * Find by full name list.
     *
     * @param nombre the nombre
     * @param ape1   the ape 1
     * @param ape2   the ape 2
     * @return the list
     */
    List<DifuntoResponseDTO> findByFullName(String nombre, String ape1, String ape2);

    /**
     * Find all by parcela list.
     *
     * @param parcelaId the parcela id
     * @return the list
     */
    List<DifuntoResponseDTO> findAllByParcela(Long parcelaId);

    /**
     * Find all by year defuncion list.
     *
     * @param year the year
     * @return the list
     */
    List<DifuntoResponseDTO> findAllByYearDefuncion(Year year);

    /**
     * Find all by ayuntamiento id list.
     *
     * @param id the id
     * @return the list
     */
    List<DifuntoResponseDTO> findAllByAyuntamientoId(Long id);

    /**
     * Find all by cementerio id list.
     *
     * @param id the id
     * @return the list
     */
    List<DifuntoResponseDTO> findAllByCementerioId(Long id);

    /**
     * Find all by cliente list.
     *
     * @param id the id
     * @return the list
     */
    List<DifuntoResponseDTO> findAllByCliente(Long id);

    /**
     * Exhumar.
     *
     * @param difuntoId the difunto id
     */
    public void exhumar(Long difuntoId);

    /**
     * Find all by cliente and ayuntamiento list.
     *
     * @param clienteId the cliente id
     * @param aytoId    the ayto id
     * @return the list
     */
    List<DifuntoResponseDTO> findAllByClienteAndAyuntamiento(Long clienteId, Long aytoId);
}
