package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.concesion.ConcesionCreateDTO;
import com.imo.cementery.model.dto.concesion.ConcesionResponseDTO;
import com.imo.cementery.model.entity.Concesion;
import org.springframework.stereotype.Component;

@Component
public class ConcesionMapper {

    public Concesion toEntity(ConcesionCreateDTO dto)
    {
        Concesion res = null;

        if(dto != null)
        {
            res = Concesion.builder()
                    .precio(dto.getPrecio())
                    .fechaInicio(dto.getFechaInicio())
                    .fechaFin(dto.getFechaFin())
                    .vencida(dto.getVencida())
                    .build();
        }

        return res;
    }

    public ConcesionResponseDTO toResponseDTO(Concesion entity)
    {
        ConcesionResponseDTO res = null;

        if(entity != null)
        {
            res = ConcesionResponseDTO.builder()
                    .id(entity.getId())
                    .precio(entity.getPrecio())
                    .fechaInicio(entity.getFechaInicio())
                    .fechaFin(entity.getFechaFin())
                    .vencida(entity.isVencida())
                    .build();
        }

        return res;
    }

    public void updateEntityFromDTO(Concesion entity, ConcesionCreateDTO dto)
    {
        if(entity != null && dto != null)
        {
            entity.setPrecio(dto.getPrecio());
            entity.setFechaInicio(dto.getFechaInicio());
            entity.setFechaFin(dto.getFechaFin());
            entity.setVencida(dto.getVencida());
        }
    }

}
