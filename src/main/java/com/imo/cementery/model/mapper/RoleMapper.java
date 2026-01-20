package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.role.RoleCreateDTO;
import com.imo.cementery.model.dto.role.RoleResponseDTO;
import com.imo.cementery.model.dto.role.RoleUpdateDTO;
import com.imo.cementery.model.entity.Role;
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
