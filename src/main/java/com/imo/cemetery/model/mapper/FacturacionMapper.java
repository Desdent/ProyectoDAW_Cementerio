package com.imo.cemetery.model.mapper;

import com.imo.cemetery.model.dto.facturacion.FacturacionCreateDTO;
import com.imo.cemetery.model.dto.facturacion.FacturacionResponseDTO;
import com.imo.cemetery.model.dto.facturacion.FacturacionUpdateDTO;
import com.imo.cemetery.model.entity.Facturacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FacturacionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "implementacionesServicios", ignore = true)
    @Mapping(target = "pago", ignore = true)
    Facturacion toEntity(FacturacionCreateDTO dto);

    @Mapping(source = "pago.id", target = "pagoId")
    FacturacionResponseDTO toResponseDTO(Facturacion entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "importe", ignore = true)
    @Mapping(target = "implementacionesServicios", ignore = true)
    @Mapping(target = "pago", ignore = true)
    void updateEntityFromDTO(FacturacionUpdateDTO dto, @MappingTarget Facturacion entity);
}
