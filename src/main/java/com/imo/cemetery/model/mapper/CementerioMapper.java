package com.imo.cemetery.model.mapper;

import com.imo.cemetery.model.dto.cementerio.CementerioCreateDTO;
import com.imo.cemetery.model.dto.cementerio.CementerioResponseDTO;
import com.imo.cemetery.model.dto.cementerio.CementerioUpdateDTO;
import com.imo.cemetery.model.entity.Cementerio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CementerioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "zonas", ignore = true)
    @Mapping(target = "serviciosOfrecidos", ignore = true)
    @Mapping(target = "ayuntamiento", ignore = true)
    Cementerio toEntity(CementerioCreateDTO dto);

    @Mapping(source = "ayuntamiento.id", target = "ayuntamientoId")
    CementerioResponseDTO toResponseDTO(Cementerio entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "zonas", ignore = true)
    @Mapping(target = "serviciosOfrecidos", ignore = true)
    void updateEntityFromDTO(CementerioUpdateDTO dto, @MappingTarget Cementerio entity);

}
