package com.imo.cemetery.service.role;

import com.imo.cemetery.model.dto.role.RoleCreateDTO;
import com.imo.cemetery.model.dto.role.RoleResponseDTO;
import com.imo.cemetery.model.dto.role.RoleUpdateDTO;
import com.imo.cemetery.model.enums.RoleType;

import java.util.List;

public interface RoleService {
    RoleResponseDTO create(RoleCreateDTO dto);
    RoleResponseDTO update(Long id, RoleUpdateDTO dto);
    void deleteById(Long id);
    RoleResponseDTO findById(Long id);
    RoleResponseDTO findByTipo(RoleType tipo);
    List<RoleResponseDTO> findAll();
}
