package com.imo.cementery.model.mapper;

import com.imo.cementery.model.dto.cliente.ClienteCreateDTO;
import com.imo.cementery.model.dto.cliente.ClienteResponseDTO;
import com.imo.cementery.model.dto.cliente.ClienteUpdateDTO;
import com.imo.cementery.model.entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteCreateDTO dto)
    {
        Cliente response = null; // Para contar con la opcion de que pueda ser nulo
        if(dto != null)
        {
            response = Cliente.builder()
                    .email(dto.getEmail())
                    .password(dto.getPassword())
                    .dni((dto.getDni()))
                    .nombre(dto.getNombre())
                    .apellido1(dto.getApellido1())
                    .apellido2(dto.getApellido2())
                    .telefono(dto.getTelefono())
                    .direccion(dto.getDireccion())
                    .localidad(dto.getLocalidad())
                    .provincia(dto.getProvincia())
                    .build();
        }

        return response;
    }

    public ClienteResponseDTO toResponseDTO(Cliente entity)
    {

        ClienteResponseDTO response = null;

        if(entity != null)
        {
            response = ClienteResponseDTO.builder()
                    .id(entity.getId())
                    .dni(entity.getDni())
                    .email(entity.getEmail())
                    .nombre(entity.getNombre())
                    .apellido1(entity.getApellido1())
                    .apellido2(entity.getApellido2())
                    .telefono(entity.getTelefono())
                    .direccion(entity.getDireccion())
                    .localidad(entity.getLocalidad())
                    .provincia(entity.getProvincia())
                    .build();
        }

        return response;
    }

    public void updateEntityFromDTO(Cliente entity, ClienteUpdateDTO dto)
    {
        if(entity != null && dto != null)
        {
            entity.setNombre(dto.getNombre());
            entity.setApellido1(dto.getApellido1());
            entity.setApellido2(dto.getApellido2());
            entity.setTelefono(dto.getTelefono());
            entity.setDireccion(dto.getDireccion());
            entity.setLocalidad(dto.getLocalidad());
            entity.setProvincia(dto.getProvincia());
        }
    }

}
