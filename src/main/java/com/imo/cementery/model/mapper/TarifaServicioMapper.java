package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.tarifaServicio.TarifaServicioCreateDTO;
import com.imo.cementery.model.dto.tarifaServicio.TarifaServicioResponseDTO;
import com.imo.cementery.model.dto.tarifaServicio.TarifaServicioUpdateDTO;
import com.imo.cementery.model.entity.TarifaServicio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TarifaServicioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cementerio", ignore = true)
    @Mapping(target = "servicio", ignore = true)
    TarifaServicio toEntity(TarifaServicioCreateDTO dto);

    @Mapping(source = "cementerio.id", target = "cementerioId")
    @Mapping(source = "servicio.id", target = "servicioId")
    TarifaServicioResponseDTO toResponseDTO(TarifaServicio entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cementerio", ignore = true)
    @Mapping(target = "servicio", ignore = true)
    void updateEntityFromDTO(TarifaServicioUpdateDTO dto, @MappingTarget TarifaServicio entity);

}
