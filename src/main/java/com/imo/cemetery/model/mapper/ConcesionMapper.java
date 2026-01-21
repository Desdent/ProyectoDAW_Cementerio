package com.imo.cemetery.model.mapper;

import com.imo.cemetery.model.dto.concesion.ConcesionCreateDTO;
import com.imo.cemetery.model.dto.concesion.ConcesionResponseDTO;
import com.imo.cemetery.model.dto.concesion.ConcesionUpdateDTO;
import com.imo.cemetery.model.entity.Concesion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ConcesionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parcelas", ignore = true)
    @Mapping(target = "pago", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    Concesion toEntity(ConcesionCreateDTO dto);

    @Mapping(source = "pago.id", target = "pagoId")
    @Mapping(source = "cliente.id", target = "clienteId")
    ConcesionResponseDTO toResponseDTO(Concesion entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parcelas", ignore = true)
    @Mapping(target = "pago", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    void updateEntityFromDTO(ConcesionUpdateDTO dto, @MappingTarget Concesion entity);
}
