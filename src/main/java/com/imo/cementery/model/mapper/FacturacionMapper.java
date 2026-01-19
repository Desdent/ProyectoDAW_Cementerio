package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.facturacion.FacturacionCreateDTO;
import com.imo.cementery.model.dto.facturacion.FacturacionResponseDTO;
import com.imo.cementery.model.dto.facturacion.FacturacionUpdateDTO;
import com.imo.cementery.model.entity.Facturacion;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FacturacionMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Facturacion toEntity(FacturacionCreateDTO dto) {
        return (dto == null) ? null : modelMapper.map(dto, Facturacion.class);
    }

    public FacturacionResponseDTO toResponseDTO(Facturacion entity) {
        return (entity == null) ? null : modelMapper.map(entity, FacturacionResponseDTO.class);
    }

    public void updateEntityFromDTO(Facturacion entity, FacturacionUpdateDTO dto) {
        if (entity != null && dto != null) modelMapper.map(dto, entity);
    }

}
