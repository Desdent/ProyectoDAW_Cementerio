package com.imo.cemetery.service.role;

import com.imo.cemetery.model.dto.role.RoleCreateDTO;
import com.imo.cemetery.model.dto.role.RoleResponseDTO;
import com.imo.cemetery.model.dto.role.RoleUpdateDTO;
import com.imo.cemetery.model.enums.RoleType;

import java.util.List;

/**
 * The interface Role service.
 */
public interface RoleService {
    /**
     * Create role response dto.
     *
     * @param dto the dto
     * @return the role response dto
     */
    RoleResponseDTO create(RoleCreateDTO dto);

    /**
     * Update role response dto.
     *
     * @param id  the id
     * @param dto the dto
     * @return the role response dto
     */
    RoleResponseDTO update(Long id, RoleUpdateDTO dto);

    /**
     * Delete by id.
     *
     * @param id the id
     */
    void deleteById(Long id);

    /**
     * Find by id role response dto.
     *
     * @param id the id
     * @return the role response dto
     */
    RoleResponseDTO findById(Long id);

    /**
     * Find by tipo role response dto.
     *
     * @param tipo the tipo
     * @return the role response dto
     */
    RoleResponseDTO findByTipo(RoleType tipo);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<RoleResponseDTO> findAll();
}
