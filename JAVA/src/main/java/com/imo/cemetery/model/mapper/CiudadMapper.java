package com.imo.cemetery.model.mapper;

import com.imo.cemetery.model.dto.ciudad.CiudadResponseDTO;
import com.imo.cemetery.model.entity.Ciudad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CiudadMapper {

    @Mapping(source = "provincia.nombre", target = "nombreProvincia")
    CiudadResponseDTO toResponseDTO(Ciudad ciudad);

    List<CiudadResponseDTO> toResponseDTOList(List<Ciudad> ciudades);
}
