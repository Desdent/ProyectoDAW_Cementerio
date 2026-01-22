package com.imo.cemetery.model.mapper;

import com.imo.cemetery.model.dto.role.RoleCreateDTO;
import com.imo.cemetery.model.dto.role.RoleResponseDTO;
import com.imo.cemetery.model.dto.role.RoleUpdateDTO;
import com.imo.cemetery.model.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    Role toEntity(RoleCreateDTO dto);

    RoleResponseDTO toResponseDTO(Role entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    void updateEntityFromDTO(RoleUpdateDTO dto, @MappingTarget Role entity);

}
