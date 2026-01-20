package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.difunto.DifuntoCreateDTO;
import com.imo.cementery.model.dto.difunto.DifuntoResponseDTO;
import com.imo.cementery.model.dto.difunto.DifuntoUpdateDTO;
import com.imo.cementery.model.entity.Difunto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DifuntoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parcela", ignore = true)
    Difunto toEntity(DifuntoCreateDTO dto);

    @Mapping(source = "parcela.id", target = "parcelaId")
    DifuntoResponseDTO toResponseDTO(Difunto entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parcela", ignore = true)
    void updateEntityFromDTO(DifuntoUpdateDTO dto, @MappingTarget Difunto entity);

}
