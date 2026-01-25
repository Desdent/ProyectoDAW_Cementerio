package com.imo.cemetery.service.cliente;

import com.imo.cemetery.model.dto.cliente.ClienteCreateDTO;
import com.imo.cemetery.model.dto.cliente.ClienteResponseDTO;
import com.imo.cemetery.model.dto.cliente.ClienteUpdateDTO;
import com.imo.cemetery.model.entity.Ciudad;
import com.imo.cemetery.model.entity.Cliente;
import com.imo.cemetery.model.entity.Role;
import com.imo.cemetery.model.enums.RoleType;
import com.imo.cemetery.model.mapper.ClienteMapper;
import com.imo.cemetery.repository.CiudadRepository;
import com.imo.cemetery.repository.ClienteRepository;
import com.imo.cemetery.repository.RoleRepository;
import com.imo.cemetery.service.ciudad.CiudadService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repo;
    private final ClienteMapper clienteMapper;
    private final RoleRepository roleRepo;
    private final CiudadService ciudadService;
    private final CiudadRepository ciudadRepo;
    private final PasswordEncoder passwordEncoder;

    // CRUD
    @Override
    @Transactional
    public ClienteResponseDTO create(ClienteCreateDTO dto) {
        ClienteResponseDTO response;

        Cliente entity = clienteMapper.toEntity(dto);

        Role role = roleRepo.findByTipo(RoleType.ROLE_CLIENTE)
                .orElseThrow(() -> new EntityNotFoundException("Rol ROLE_CLIENTE no encontrado"));
        entity.setRole(role);

        entity.setPassword(passwordEncoder.encode(dto.getPassword()));

        Ciudad ciudad = ciudadRepo.findById(dto.getCiudadId())
                .orElseThrow(() -> new EntityNotFoundException("Ciudad no encontrada"));
        entity.setCiudad(ciudad);

        response = clienteMapper.toResponseDTO(repo.save(entity));
        return response;
    }

    @Override
    public void deleteById(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("No existe el ID: " + id); // #TODO cambiar por excepcion personaizada
        }
        repo.deleteById(id);
    }

    @Override
    @Transactional
    public ClienteResponseDTO update(ClienteUpdateDTO dto, Long id) {

        Cliente entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe el ID:" + id)); // #TODO cambiar por excepcion personaizada

        clienteMapper.updateEntityFromDTO(dto, entity);

        repo.save(entity);

        return clienteMapper.toResponseDTO(entity);
    }



    // Consultas

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
    public ClienteResponseDTO findByDni(String dni) {
        return repo.findByDni(dni).map(clienteMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Cliente con dni: " + dni + " no encontrado."));
    }

    @Override
    public ClienteResponseDTO findByEmail(String email) {
        return repo.findByEmail(email).map(clienteMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Cliente con email: " + email + " no encontrado."));
    }


    // Búsquedas y filtros

    @Override
    public List<ClienteResponseDTO> search(String term) {
        return repo.findAllByNombreContainingIgnoreCaseOrApellido1ContainingIgnoreCaseOrApellido2ContainingIgnoreCaseOrTelefonoContaining(term, term, term, term)
                .stream().map(clienteMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<ClienteResponseDTO> findByCiudadId(Long id) {
        return repo.findAllByCiudadId(id)
                .stream()
                .map(clienteMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<ClienteResponseDTO> findByCiudadNombre(String nombre) {
        return repo.findAllByCiudadNombreIgnoreCase(nombre)
                .stream()
                .map(clienteMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<ClienteResponseDTO> findByProvinciaId(Long id) {
        return repo.findAllByCiudadProvinciaId(id)
                .stream()
                .map(clienteMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<ClienteResponseDTO> findByProvinciaNombre(String nombre) {
        return repo.findAllByCiudadProvinciaNombreIgnoreCase(nombre)
                .stream()
                .map(clienteMapper::toResponseDTO)
                .toList();
    }


}
