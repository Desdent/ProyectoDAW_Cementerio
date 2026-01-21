package com.imo.cementery.service.cliente;

import com.imo.cementery.model.dto.cliente.ClienteCreateDTO;
import com.imo.cementery.model.dto.cliente.ClienteResponseDTO;
import com.imo.cementery.model.dto.cliente.ClienteUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    List<ClienteResponseDTO> findAll();

    Optional<ClienteResponseDTO> findById(Long id);

    ClienteResponseDTO create(ClienteCreateDTO dto);

    void deleteById(Long id);

    ClienteResponseDTO update(ClienteUpdateDTO dto, Long id);

}
