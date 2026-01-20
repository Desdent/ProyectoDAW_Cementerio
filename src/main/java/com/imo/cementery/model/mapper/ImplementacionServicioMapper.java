package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.implementacionServicio.ImplementacionServicioCreateDTO;
import com.imo.cementery.model.dto.implementacionServicio.ImplementacionServicioResponseDTO;
import com.imo.cementery.model.dto.implementacionServicio.ImplementacionServicioUpdateDTO;
import com.imo.cementery.model.entity.ImplementacionServicio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ImplementacionServicioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parcela", ignore = true)
    @Mapping(target = "servicio", ignore = true)
    @Mapping(target = "facturacion", ignore = true)
    ImplementacionServicio toEntity(ImplementacionServicioCreateDTO dto);

    @Mapping(source = "parcela.id", target = "parcelaId")
    @Mapping(source = "servicio.id", target = "servicioId")
    @Mapping(source = "facturacion.id", target = "facturacionId")
    ImplementacionServicioResponseDTO toResponseDTO(ImplementacionServicio entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parcela", ignore = true)
    @Mapping(target = "servicio", ignore = true)
    @Mapping(target = "facturacion", ignore = true)
    void updateEntityFromDTO(ImplementacionServicioUpdateDTO dto, @MappingTarget ImplementacionServicio entity);

}
