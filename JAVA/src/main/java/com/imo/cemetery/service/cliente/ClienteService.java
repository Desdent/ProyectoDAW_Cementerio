package com.imo.cemetery.service.cliente;

import com.imo.cemetery.model.dto.cliente.ClienteCreateDTO;
import com.imo.cemetery.model.dto.cliente.ClienteResponseDTO;
import com.imo.cemetery.model.dto.cliente.ClienteUpdateDTO;
import com.imo.cemetery.model.entity.Cliente;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    // CRUD
    ClienteResponseDTO create(ClienteCreateDTO dto);
    ClienteResponseDTO update(ClienteUpdateDTO dto, Long id);
    void deleteById(Long id);

    // Consultas
    List<ClienteResponseDTO> findAll();

    ClienteResponseDTO findById(Long id);
    ClienteResponseDTO findByDni(String dni);
    ClienteResponseDTO findByEmail(String email);


    // Búsquedas y Filtros
    // Este usará el findByNombre...Containing que pusimos en el repo
    List<ClienteResponseDTO> search(String term);
    List<ClienteResponseDTO> findByCiudadId(Long ciudadId);
    List<ClienteResponseDTO> findByCiudadNombre(String ciudadNombre);
    List<ClienteResponseDTO> findByProvinciaId(Long provinciaId);
    List<ClienteResponseDTO> findByProvinciaNombre(String provinciaNombre);
}
