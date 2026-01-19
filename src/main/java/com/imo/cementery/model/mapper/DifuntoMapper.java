package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.difunto.DifuntoCreateDTO;
import com.imo.cementery.model.dto.difunto.DifuntoResponseDTO;
import com.imo.cementery.model.dto.difunto.DifuntoUpdateDTO;
import com.imo.cementery.model.entity.Difunto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DifuntoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Difunto toEntity(DifuntoCreateDTO dto) {
        return (dto == null) ? null : modelMapper.map(dto, Difunto.class);
    }

    public DifuntoResponseDTO toResponseDTO(Difunto entity) {
        return (entity == null) ? null : modelMapper.map(entity, DifuntoResponseDTO.class);
    }

    public void updateEntityFromDTO(Difunto entity, DifuntoUpdateDTO dto) {
        if (entity != null && dto != null) modelMapper.map(dto, entity);
    }

}
