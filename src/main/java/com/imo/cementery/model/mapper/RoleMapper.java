package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.role.RoleCreateDTO;
import com.imo.cementery.model.dto.role.RoleResponseDTO;
import com.imo.cementery.model.dto.role.RoleUpdateDTO;
import com.imo.cementery.model.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Role toEntity(RoleCreateDTO dto) {
        return (dto == null) ? null : modelMapper.map(dto, Role.class);
    }

    public RoleResponseDTO toResponseDTO(Role entity) {
        return (entity == null) ? null : modelMapper.map(entity, RoleResponseDTO.class);
    }

    public void updateEntityFromDTO(Role entity, RoleUpdateDTO dto) {
        if (entity != null && dto != null) modelMapper.map(dto, entity);
    }

}
