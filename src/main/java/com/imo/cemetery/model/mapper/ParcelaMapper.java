package com.imo.cemetery.model.mapper;

import com.imo.cemetery.model.dto.parcela.ParcelaCreateDTO;
import com.imo.cemetery.model.dto.parcela.ParcelaResponseDTO;
import com.imo.cemetery.model.dto.parcela.ParcelaUpdateDTO;
import com.imo.cemetery.model.entity.Parcela;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ParcelaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "difuntos", ignore = true)
    @Mapping(target = "implementacionesEnParcela", ignore = true)
    @Mapping(target = "concesion", ignore = true)
    @Mapping(target = "zona", ignore = true)
    Parcela toEntity(ParcelaCreateDTO dto);

    @Mapping(source = "concesion.id", target = "concesionId")
    @Mapping(source = "zona.id", target = "zonaId")
    ParcelaResponseDTO toResponseDTO(Parcela entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "difuntos", ignore = true)
    @Mapping(target = "implementacionesEnParcela", ignore = true)
    @Mapping(target = "concesion", ignore = true)
    @Mapping(target = "zona", ignore = true)
    void updateEntityFromDTO(ParcelaUpdateDTO dto, @MappingTarget Parcela entity);

}
