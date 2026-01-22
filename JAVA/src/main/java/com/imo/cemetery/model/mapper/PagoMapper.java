package com.imo.cemetery.model.mapper;

import com.imo.cemetery.model.dto.pago.PagoCreateDTO;
import com.imo.cemetery.model.dto.pago.PagoResponseDTO;
import com.imo.cemetery.model.dto.pago.PagoUpdateDTO;
import com.imo.cemetery.model.entity.Pago;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PagoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "facturacion", ignore = true)
    @Mapping(target = "concesion", ignore = true)
    Pago toEntity(PagoCreateDTO dto);

    PagoResponseDTO toResponseDTO(Pago entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "facturacion", ignore = true)
    @Mapping(target = "concesion", ignore = true)
    void updateEntityFromDTO(PagoUpdateDTO dto, @MappingTarget Pago entity);

}
