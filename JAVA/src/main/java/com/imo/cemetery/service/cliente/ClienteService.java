package com.imo.cemetery.service.cliente;

import com.imo.cemetery.model.dto.cliente.ClienteCreateDTO;
import com.imo.cemetery.model.dto.cliente.ClienteResponseDTO;
import com.imo.cemetery.model.dto.cliente.ClienteUpdateDTO;
import com.imo.cemetery.model.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    List<ClienteResponseDTO> findAll();

    ClienteResponseDTO findById(Long id);

    ClienteResponseDTO create(ClienteCreateDTO dto);

    void deleteById(Long id);

    ClienteResponseDTO update(ClienteUpdateDTO dto, Long id);

    Cliente getEntityById(Long id);

}
