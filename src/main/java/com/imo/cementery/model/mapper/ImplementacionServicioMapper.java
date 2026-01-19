package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.implementacionServicio.ImplementacionServicioCreateDTO;
import com.imo.cementery.model.dto.implementacionServicio.ImplementacionServicioResponseDTO;
import com.imo.cementery.model.dto.implementacionServicio.ImplementacionServicioUpdateDTO;
import com.imo.cementery.model.entity.ImplementacionServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImplementacionServicioMapper {

    @Autowired
    private ModelMapper modelMapper;

    public ImplementacionServicio toEntity(ImplementacionServicioCreateDTO dto) {
        return (dto == null) ? null : modelMapper.map(dto, ImplementacionServicio.class);
    }

    public ImplementacionServicioResponseDTO toResponseDTO(ImplementacionServicio entity) {
        return (entity == null) ? null : modelMapper.map(entity, ImplementacionServicioResponseDTO.class);
    }

    public void updateEntityFromDTO(ImplementacionServicio entity, ImplementacionServicioUpdateDTO dto) {
        if (entity != null && dto != null) modelMapper.map(dto, entity);
    }

}
