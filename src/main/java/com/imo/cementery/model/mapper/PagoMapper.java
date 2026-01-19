package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.pago.PagoCreateDTO;
import com.imo.cementery.model.dto.pago.PagoResponseDTO;
import com.imo.cementery.model.dto.pago.PagoUpdateDTO;
import com.imo.cementery.model.entity.Pago;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PagoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Pago toEntity(PagoCreateDTO dto) {
        return (dto == null) ? null : modelMapper.map(dto, Pago.class);
    }

    public PagoResponseDTO toResponseDTO(Pago entity) {
        return (entity == null) ? null : modelMapper.map(entity, PagoResponseDTO.class);
    }

    public void updateEntityFromDTO(Pago entity, PagoUpdateDTO dto) {
        if (entity != null && dto != null) modelMapper.map(dto, entity);
    }

}
