package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.cementerio.CementerioCreateDTO;
import com.imo.cementery.model.dto.cementerio.CementerioResponseDTO;
import com.imo.cementery.model.dto.cementerio.CementerioUpdateDTO;
import com.imo.cementery.model.entity.Cementerio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CementerioMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Cementerio toEntity(CementerioCreateDTO dto) {
        return (dto == null) ? null : modelMapper.map(dto, Cementerio.class);
    }

    public CementerioResponseDTO toResponseDTO(Cementerio entity) {
        return (entity == null) ? null : modelMapper.map(entity, CementerioResponseDTO.class);
    }

    public void updateEntityFromDTO(Cementerio entity, CementerioUpdateDTO dto) {
        if (entity != null && dto != null) modelMapper.map(dto, entity);
    }

}
