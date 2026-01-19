package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.concesion.ConcesionCreateDTO;
import com.imo.cementery.model.dto.concesion.ConcesionResponseDTO;
import com.imo.cementery.model.dto.concesion.ConcesionUpdateDTO;
import com.imo.cementery.model.entity.Concesion;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConcesionMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Concesion toEntity(ConcesionCreateDTO dto) {
        return (dto == null) ? null : modelMapper.map(dto, Concesion.class);
    }

    public ConcesionResponseDTO toResponseDTO(Concesion entity) {
        return (entity == null) ? null : modelMapper.map(entity, ConcesionResponseDTO.class);
    }

    public void updateEntityFromDTO(Concesion entity, ConcesionUpdateDTO dto) {
        if (entity != null && dto != null) modelMapper.map(dto, entity);
    }

}
