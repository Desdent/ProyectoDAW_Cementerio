package com.imo.cemetery.service.zona;

import com.imo.cemetery.model.dto.zona.ZonaCreateDTO;
import com.imo.cemetery.model.dto.zona.ZonaResponseDTO;
import com.imo.cemetery.model.dto.zona.ZonaUpdateDTO;

import java.util.List;

/**
 * The interface Zona service.
 */
public interface ZonaService {
    /**
     * Create zona response dto.
     *
     * @param dto the dto
     * @return the zona response dto
     */
    ZonaResponseDTO create(ZonaCreateDTO dto);

    /**
     * Update zona response dto.
     *
     * @param id  the id
     * @param dto the dto
     * @return the zona response dto
     */
    ZonaResponseDTO update(Long id, ZonaUpdateDTO dto);

    /**
     * Delete by id.
     *
     * @param id the id
     */
    void deleteById(Long id);

    /**
     * Find by id zona response dto.
     *
     * @param id the id
     * @return the zona response dto
     */
    ZonaResponseDTO findById(Long id);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<ZonaResponseDTO> findAll();

    /**
     * Find all by cementerio id list.
     *
     * @param cementerioId the cementerio id
     * @return the list
     */
    List<ZonaResponseDTO> findAllByCementerioId(Long cementerioId);

    /**
     * Search by nombre list.
     *
     * @param nombre the nombre
     * @return the list
     */
    List<ZonaResponseDTO> searchByNombre(String nombre);

    /**
     * Find by nombre en cementerio list.
     *
     * @param nombre       the nombre
     * @param cementerioId the cementerio id
     * @return the list
     */
    List<ZonaResponseDTO> findByNombreEnCementerio(String nombre, Long cementerioId);
}
