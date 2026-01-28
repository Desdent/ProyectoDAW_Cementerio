package com.imo.cemetery.model.mapper;

import com.imo.cemetery.model.dto.servicio.ServicioCreateDTO;
import com.imo.cemetery.model.dto.servicio.ServicioResponseDTO;
import com.imo.cemetery.model.dto.servicio.ServicioUpdateDTO;
import com.imo.cemetery.model.entity.Servicio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServicioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "disponibilidadEnCementerios", ignore = true)
    @Mapping(target = "implementacionesServicios", ignore = true)
    Servicio toEntity(ServicioCreateDTO dto);

    ServicioResponseDTO toResponseDTO(Servicio entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "disponibilidadEnCementerios", ignore = true)
    @Mapping(target = "implementacionesServicios", ignore = true)
    void updateEntityFromDTO(ServicioUpdateDTO dto, @MappingTarget Servicio entity);

}
