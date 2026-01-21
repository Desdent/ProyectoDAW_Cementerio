package com.imo.cemetery.service.cliente;

import com.imo.cemetery.model.dto.cliente.ClienteCreateDTO;
import com.imo.cemetery.model.dto.cliente.ClienteResponseDTO;
import com.imo.cemetery.model.dto.cliente.ClienteUpdateDTO;
import com.imo.cemetery.model.entity.Cliente;
import com.imo.cemetery.model.mapper.ClienteMapper;
import com.imo.cemetery.repository.ClienteRepository;
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

    @Override
    public List<ClienteResponseDTO> findAll() {
        return repo.findAll().stream()
                .map(clienteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClienteResponseDTO> findById(Long id) {
        return repo.findById(id).map(clienteMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public ClienteResponseDTO create(ClienteCreateDTO dto) {

        Cliente entity = clienteMapper.toEntity(dto);

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
}
