package com.imo.cemetery.model.mapper;

import com.imo.cemetery.model.dto.provincia.ProvinciaResponseDTO;
import com.imo.cemetery.model.entity.Provincia;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProvinciaMapper {
    ProvinciaResponseDTO toResponseDTO(Provincia provincia);

    List<ProvinciaResponseDTO> toResponseDTOList(List<Provincia> provincias);
}
