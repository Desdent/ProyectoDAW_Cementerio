package com.imo.cemetery.model.mapper;

import com.imo.cemetery.model.dto.user.UserCreateDTO;
import com.imo.cemetery.model.dto.user.UserResponseDTO;
import com.imo.cemetery.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
        // Se ignora el campo ID en las creaciones porque se genera en la BBDD
    User toEntity(UserCreateDTO dto);

    UserResponseDTO toResponseDTO(User entity);

}
