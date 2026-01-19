package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.ayuntamiento.AyuntamientoCreateDTO;
import com.imo.cementery.model.dto.ayuntamiento.AyuntamientoResponseDTO;
import com.imo.cementery.model.dto.ayuntamiento.AyuntamientoUpdateDTO;
import com.imo.cementery.model.entity.Ayuntamiento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AyuntamientoMapper {

    @Autowired
    private ModelMapper modelMapper;

    // Mapea en el segundo arguneto lo que hay en el primero (el objeto que debe crear y buildear con los mismos campos que el primer argumento)
    public Ayuntamiento toEntity(AyuntamientoCreateDTO dto) {
        return (dto == null) ? null : modelMapper.map(dto, Ayuntamiento.class);
    }

    // Mapea en el segundo arguneto lo que hay en el primero (el objeto que debe crear y buildear con los mismos campos que el primer argumento)
    public AyuntamientoResponseDTO toResponseDTO(Ayuntamiento entity) {
        return (entity == null) ? null : modelMapper.map(entity, AyuntamientoResponseDTO.class);
    }

    // Mapea en el segundo arguneto lo que hay en el primero, pero en este caso no devuelve nada, solo lo edita
    public void updateEntityFromDTO(Ayuntamiento entity, AyuntamientoUpdateDTO dto) {
        if (entity != null && dto != null) modelMapper.map(dto, entity);
    }
}
