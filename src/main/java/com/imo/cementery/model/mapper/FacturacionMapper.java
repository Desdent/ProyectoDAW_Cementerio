package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.facturacion.FacturacionCreateDTO;
import com.imo.cementery.model.dto.facturacion.FacturacionResponseDTO;
import com.imo.cementery.model.dto.facturacion.FacturacionUpdateDTO;
import com.imo.cementery.model.entity.Facturacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FacturacionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "implementacionServicios", ignore = true)
    @Mapping(target = "pago", ignore = true)
    Facturacion toEntity(FacturacionCreateDTO dto);

    @Mapping(source = "pago.id", target = "pagoId")
    FacturacionResponseDTO toResponseDTO(Facturacion entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "importe", ignore = true)
    @Mapping(target = "implementacionServicios", ignore = true)
    @Mapping(target = "pago", ignore = true)
    void updateEntityFromDTO(FacturacionUpdateDTO dto, Facturacion entity);
}
