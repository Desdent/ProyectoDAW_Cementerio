package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.servicio.ServicioCreateDTO;
import com.imo.cementery.model.dto.servicio.ServicioResponseDTO;
import com.imo.cementery.model.dto.servicio.ServicioUpdateDTO;
import com.imo.cementery.model.entity.Servicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServicioMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Servicio toEntity(ServicioCreateDTO dto) {
        return (dto == null) ? null : modelMapper.map(dto, Servicio.class);
    }

    public ServicioResponseDTO toResponseDTO(Servicio entity) {
        return (entity == null) ? null : modelMapper.map(entity, ServicioResponseDTO.class);
    }

    public void updateEntityFromDTO(Servicio entity, ServicioUpdateDTO dto) {
        if (entity != null && dto != null) modelMapper.map(dto, entity);
    }

}
