package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.ayuntamiento.AyuntamientoCreateDTO;
import com.imo.cementery.model.dto.ayuntamiento.AyuntamientoResponseDTO;
import com.imo.cementery.model.dto.ayuntamiento.AyuntamientoUpdateDTO;
import com.imo.cementery.model.entity.Ayuntamiento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AyuntamientoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cementerios", ignore = true)
    Ayuntamiento toEntity(AyuntamientoCreateDTO dto);

    AyuntamientoResponseDTO toResponseDTO(Ayuntamiento ayuntamiento);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "nif", ignore = true)
    @Mapping(target = "cementerios", ignore = true)
    void updateEntityFromDto(AyuntamientoUpdateDTO dto, @MappingTarget Ayuntamiento ayuntamiento);
}