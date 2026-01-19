package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.parcela.ParcelaCreateDTO;
import com.imo.cementery.model.dto.parcela.ParcelaResponseDTO;
import com.imo.cementery.model.dto.parcela.ParcelaUpdateDTO;
import com.imo.cementery.model.entity.Parcela;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParcelaMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Parcela toEntity(ParcelaCreateDTO dto) {
        return (dto == null) ? null : modelMapper.map(dto, Parcela.class);
    }

    public ParcelaResponseDTO toResponseDTO(Parcela entity) {
        return (entity == null) ? null : modelMapper.map(entity, ParcelaResponseDTO.class);
    }

    public void updateEntityFromDTO(Parcela entity, ParcelaUpdateDTO dto) {
        if (entity != null && dto != null) modelMapper.map(dto, entity);
    }

}
