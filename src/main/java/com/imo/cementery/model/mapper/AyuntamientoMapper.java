package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.ayuntamiento.AyuntamientoCreateDTO;
import com.imo.cementery.model.dto.ayuntamiento.AyuntamientoResponseDTO;
import com.imo.cementery.model.dto.ayuntamiento.AyuntamientoUpdateDTO;
import com.imo.cementery.model.entity.Ayuntamiento;
import org.springframework.stereotype.Component;

@Component
public class AyuntamientoMapper {

    public Ayuntamiento toEntity(AyuntamientoCreateDTO dto)
    {
        Ayuntamiento res = null;

        if(dto != null)
        {
            res = Ayuntamiento.builder()
                    .email(dto.getEmail())
                    .password(dto.getPassword())
                    .nif(dto.getNif())
                    .nombre(dto.getNombre())
                    .telefono(dto.getTelefono())
                    .direccion(dto.getDireccion())
                    .escudo(dto.getEscudo())
                    .localidad(dto.getLocalidad())
                    .provincia(dto.getProvincia())
                    .build();
        }

        return res;
    }

    public AyuntamientoResponseDTO toResponseDTO(Ayuntamiento entity)
    {
        AyuntamientoResponseDTO res = null;

        if(entity != null)
        {
            res = AyuntamientoResponseDTO.builder()
                    .id(entity.getId())
                    .nif(entity.getNif())
                    .nombre(entity.getNombre())
                    .telefono(entity.getTelefono())
                    .direccion(entity.getDireccion())
                    .escudo(entity.getEscudo())
                    .localidad(entity.getLocalidad())
                    .provincia(entity.getProvincia())
                    .build();
        }

        return res;
    }

    public void updateEntityFromDTO(Ayuntamiento entity, AyuntamientoUpdateDTO dto)
    {
        if(entity != null && dto != null)
        {
            entity.setNombre(dto.getNombre());
            entity.setTelefono(dto.getTelefono());
            entity.setDireccion(dto.getDireccion());
            entity.setEscudo(dto.getEscudo());
            entity.setLocalidad(dto.getLocalidad());
            entity.setProvincia(dto.getProvincia());
        }
    }
}
