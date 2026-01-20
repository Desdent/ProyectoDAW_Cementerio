package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.parcela.ParcelaCreateDTO;
import com.imo.cementery.model.dto.parcela.ParcelaResponseDTO;
import com.imo.cementery.model.dto.parcela.ParcelaUpdateDTO;
import com.imo.cementery.model.entity.Parcela;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ParcelaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "difuntos", ignore = true)
    @Mapping(target = "implementacionesEnParcela")
    Parcela toEntity(ParcelaCreateDTO dto);

    @Mapping(source = "concesion.id", target = "concesionId")
    @Mapping(source = "zona.id", target = "zonaId")
    ParcelaResponseDTO toResponseDTO(Parcela entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "difuntos", ignore = true)
    @Mapping(target = "implementacionesEnParcela")
    void updateEntityFromDTO(ParcelaUpdateDTO dto, @MappingTarget Parcela entity);

}
