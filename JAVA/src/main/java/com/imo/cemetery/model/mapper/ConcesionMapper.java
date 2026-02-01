package com.imo.cemetery.model.mapper;

import com.imo.cemetery.model.dto.concesion.ConcesionCreateDTO;
import com.imo.cemetery.model.dto.concesion.ConcesionResponseDTO;
import com.imo.cemetery.model.dto.concesion.ConcesionUpdateDTO;
import com.imo.cemetery.model.entity.Concesion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConcesionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parcelas", ignore = true)
    @Mapping(target = "pago", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    Concesion toEntity(ConcesionCreateDTO dto);

    @Mapping(source = "pago.id", target = "pagoId")
    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "parcelas", target = "parcelaIds")
    ConcesionResponseDTO toResponseDTO(Concesion entity);
    default List<Long> mapParcelasToIds(List<com.imo.cemetery.model.entity.Parcela> parcelas) {
        if (parcelas == null) {
            return null;
        }
        return parcelas.stream()
                .map(p -> p.getId())
                .toList();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parcelas", ignore = true)
    @Mapping(target = "pago", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    void updateEntityFromDTO(ConcesionUpdateDTO dto, @MappingTarget Concesion entity);
}
