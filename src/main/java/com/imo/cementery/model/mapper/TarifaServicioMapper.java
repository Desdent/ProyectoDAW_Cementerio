package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.tatifaServicio.TarifaServicioCreateDTO;
import com.imo.cementery.model.dto.tatifaServicio.TarifaServicioResponseDTO;
import com.imo.cementery.model.dto.tatifaServicio.TarifaServicioUpdateDTO;
import com.imo.cementery.model.entity.TarifaServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TarifaServicioMapper {

    @Autowired
    private ModelMapper modelMapper;

    public TarifaServicio toEntity(TarifaServicioCreateDTO dto) {
        return (dto == null) ? null : modelMapper.map(dto, TarifaServicio.class);
    }

    public TarifaServicioResponseDTO toResponseDTO(TarifaServicio entity) {
        return (entity == null) ? null : modelMapper.map(entity, TarifaServicioResponseDTO.class);
    }

    public void updateEntityFromDTO(TarifaServicio entity, TarifaServicioUpdateDTO dto) {
        if (entity != null && dto != null) modelMapper.map(dto, entity);
    }

}
