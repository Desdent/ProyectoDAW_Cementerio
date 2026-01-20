package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.cliente.ClienteCreateDTO;
import com.imo.cementery.model.dto.cliente.ClienteResponseDTO;
import com.imo.cementery.model.dto.cliente.ClienteUpdateDTO;
import com.imo.cementery.model.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "concesiones", ignore = true)
    Cliente toEntity(ClienteCreateDTO dto);

    ClienteResponseDTO toResponseDTO(Cliente cliente);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "dni", ignore = true)
    @Mapping(target = "concesiones", ignore = true)
    void updateEntityFromDto(ClienteUpdateDTO dto, @MappingTarget Cliente cliente);
}
