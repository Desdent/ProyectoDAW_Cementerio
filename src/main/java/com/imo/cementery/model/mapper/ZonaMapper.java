package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.zona.ZonaCreateDTO;
import com.imo.cementery.model.dto.zona.ZonaResponseDTO;
import com.imo.cementery.model.dto.zona.ZonaUpdateDTO;
import com.imo.cementery.model.entity.Zona;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ZonaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parcelas", ignore = true)
    @Mapping(target = "cementerio", ignore = true)
    Zona toEntity(ZonaCreateDTO dto);

    @Mapping(source = "cementerio.id", target = "cementerioId")
    ZonaResponseDTO toResponseDTO(Zona entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parcelas", ignore = true)
    @Mapping(target = "cementerio", ignore = true)
    void updateEntityFromDTO(ZonaUpdateDTO dto, @MappingTarget Zona entity);

}
