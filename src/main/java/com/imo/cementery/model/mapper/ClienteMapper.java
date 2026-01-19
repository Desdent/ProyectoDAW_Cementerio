package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.cliente.ClienteCreateDTO;
import com.imo.cementery.model.dto.cliente.ClienteResponseDTO;
import com.imo.cementery.model.dto.cliente.ClienteUpdateDTO;
import com.imo.cementery.model.entity.Cliente;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Cliente toEntity(ClienteCreateDTO dto) {
        return (dto == null) ? null : modelMapper.map(dto, Cliente.class);
    }

    public ClienteResponseDTO toResponseDTO(Cliente entity) {
        return (entity == null) ? null : modelMapper.map(entity, ClienteResponseDTO.class);
    }

    public void updateEntityFromDTO(Cliente entity, ClienteUpdateDTO dto) {
        if (entity != null && dto != null) modelMapper.map(dto, entity);
    }

}
