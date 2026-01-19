package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.difunto.DifuntoCreateDTO;
import com.imo.cementery.model.dto.difunto.DifuntoResponseDTO;
import com.imo.cementery.model.dto.difunto.DifuntoUpdateDTO;
import com.imo.cementery.model.entity.Difunto;
import org.springframework.stereotype.Component;

@Component
public class DifuntoMapper {

    public Difunto toEntity(DifuntoCreateDTO dto)
    {
        Difunto res = null;

        if(dto != null)
        {
            res = Difunto.builder()
                    .nombre(dto.getNombre())
                    .apellido1(dto.getApellido1())
                    .apellido2(dto.getApellido2())
                    .yearNacimiento(dto.getYearNacimiento())
                    .yearDefuncion(dto.getYearDefuncion())
                    .mensaje(dto.getMensaje())
                    .foto(dto.getFoto())
                    .build();
        }

        return res;
    }

    public DifuntoResponseDTO toResponseDTO(Difunto entity)
    {
        DifuntoResponseDTO res = null;

        if(entity != null)
        {
            res = DifuntoResponseDTO.builder()
                    .nombre(entity.getNombre())
                    .apellido1(entity.getApellido1())
                    .apellido2(entity.getApellido2())
                    .yearNacimiento(entity.getYearNacimiento())
                    .yearDefuncion(entity.getYearDefuncion())
                    .mensaje(entity.getMensaje())
                    .foto(entity.getFoto())
                    .build();
        }

        return res;
    }

    public void updateEntityFromDTO(Difunto entity, DifuntoUpdateDTO dto)
    {
        if(entity != null && dto != null)
        {
            entity.setNombre(dto.getNombre());
            entity.setApellido1(dto.getApellido1());
            entity.setApellido2(dto.getApellido2());
            entity.setYearNacimiento(dto.getYearNacimiento());
            entity.setYearDefuncion(dto.getYearDefuncion());
            entity.setMensaje(dto.getMensaje());
            entity.setFoto(dto.getFoto());
        }
    }

}
