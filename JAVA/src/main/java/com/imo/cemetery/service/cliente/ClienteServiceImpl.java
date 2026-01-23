package com.imo.cemetery.service.cliente;

import com.imo.cemetery.model.dto.cliente.ClienteCreateDTO;
import com.imo.cemetery.model.dto.cliente.ClienteResponseDTO;
import com.imo.cemetery.model.dto.cliente.ClienteUpdateDTO;
import com.imo.cemetery.model.entity.Ciudad;
import com.imo.cemetery.model.entity.Cliente;
import com.imo.cemetery.model.entity.Role;
import com.imo.cemetery.model.enums.RoleType;
import com.imo.cemetery.model.mapper.CiudadMapper;
import com.imo.cemetery.model.mapper.ClienteMapper;
import com.imo.cemetery.repository.CiudadRepository;
import com.imo.cemetery.repository.ClienteRepository;
import com.imo.cemetery.repository.RoleRepository;
import com.imo.cemetery.service.ciudad.CiudadService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repo;
    private final ClienteMapper clienteMapper;
    private final RoleRepository roleRepo;
    private final CiudadService ciudadService;
    private final CiudadRepository ciudadRepo;

    @Override
    public List<ClienteResponseDTO> findAll() {
        return repo.findAll().stream()
                .map(clienteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteResponseDTO findById(Long id) {
        return repo.findById(id).map(clienteMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado: " +  id));
    }

    @Override
    @Transactional
    public ClienteResponseDTO create(ClienteCreateDTO dto) {

        Cliente entity = clienteMapper.toEntity(dto);

        Role role = roleRepo.findByTipo(RoleType.ROLE_CLIENTE)
                .orElseThrow(() -> new RuntimeException("Rol ROLE_CLIENTE no encontrado"));
        entity.setRole(role);

        entity.setCiudad(ciudadRepo.getReferenceById(dto.getCiudadId()));

        Cliente entitySaved = repo.save(entity);

        return clienteMapper.toResponseDTO(entitySaved);
    }

    @Override
    public void deleteById(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("No existe el ID: " + id); // #TODO cambiar por excepcion personaizada
        }
        repo.deleteById(id);
    }

    @Override
    @Transactional
    public ClienteResponseDTO update(ClienteUpdateDTO dto, Long id) {

        Cliente entity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el ID:" + id)); // #TODO cambiar por excepcion personaizada

        clienteMapper.updateEntityFromDTO(dto, entity);

        repo.save(entity);

        return clienteMapper.toResponseDTO(entity);
    }

    @Override
    public Cliente getEntityById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error interno: Cliente con ID " + id + " no existe"));
    }
}
