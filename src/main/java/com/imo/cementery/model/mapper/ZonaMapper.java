package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.zona.ZonaCreateDTO;
import com.imo.cementery.model.dto.zona.ZonaResponseDTO;
import com.imo.cementery.model.dto.zona.ZonaUpdateDTO;
import com.imo.cementery.model.entity.Zona;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ZonaMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Zona toEntity(ZonaCreateDTO dto) {
        return (dto == null) ? null : modelMapper.map(dto, Zona.class);
    }

    public ZonaResponseDTO toResponseDTO(Zona entity) {
        return (entity == null) ? null : modelMapper.map(entity, ZonaResponseDTO.class);
    }

    public void updateEntityFromDTO(Zona entity, ZonaUpdateDTO dto) {
        if (entity != null && dto != null) modelMapper.map(dto, entity);
    }

}
