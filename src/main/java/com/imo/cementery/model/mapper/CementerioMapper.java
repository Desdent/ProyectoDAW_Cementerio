package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.cementerio.CementerioCreateDTO;
import com.imo.cementery.model.dto.cementerio.CementerioResponseDTO;
import com.imo.cementery.model.dto.cementerio.CementerioUpdateDTO;
import com.imo.cementery.model.entity.Cementerio;
import org.springframework.stereotype.Component;

@Component
public class CementerioMapper {

    public Cementerio toEntity(CementerioCreateDTO dto)
    {
        Cementerio res = null;

        if(dto != null){
            res = Cementerio.builder()
                    .nombre(dto.getNombre())
                    .direccion(dto.getDireccion())
                    .telefono(dto.getTelefono())
                    .build();
        }

        return res;
    }

    public CementerioResponseDTO toResponseDTO(Cementerio entity)
    {
        CementerioResponseDTO res = null;

        if(entity != null)
        {
            res = CementerioResponseDTO.builder()
                    .id(entity.getId())
                    .nombre(entity.getNombre())
                    .direccion(entity.getDireccion())
                    .telefono(entity.getTelefono())
                    .build();
        }

        return res;
    }

    public void upateEntityFromDTO(Cementerio entity, CementerioUpdateDTO dto)
    {
        if (entity != null && dto != null)
        {
            entity.setNombre(dto.getNombre());
            entity.setDireccion(dto.getDireccion());
            entity.setTelefono(dto.getTelefono());
        }
    }

}
