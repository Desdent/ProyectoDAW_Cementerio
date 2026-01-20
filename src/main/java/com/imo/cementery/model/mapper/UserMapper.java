package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.ayuntamiento.AyuntamientoCreateDTO;
import com.imo.cementery.model.dto.ayuntamiento.AyuntamientoResponseDTO;
import com.imo.cementery.model.entity.Ayuntamiento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
        // Se ignora el campo ID en las creaciones porque se genera en la BBDD
    Ayuntamiento toEntity(AyuntamientoCreateDTO dto);

    AyuntamientoResponseDTO toResponseDTO(Ayuntamiento entity);

}
