package com.imo.cemetery.service.role;

import com.imo.cemetery.model.dto.role.RoleCreateDTO;
import com.imo.cemetery.model.dto.role.RoleResponseDTO;
import com.imo.cemetery.model.dto.role.RoleUpdateDTO;
import com.imo.cemetery.model.entity.Role;
import com.imo.cemetery.model.enums.RoleType;
import com.imo.cemetery.model.mapper.RoleMapper;
import com.imo.cemetery.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repo;
    private final RoleMapper mapper;

    @Override
    @Transactional
    public RoleResponseDTO create(RoleCreateDTO dto) {
        if (repo.existsByTipo(dto.getTipo())) {
            throw new IllegalStateException("El rol " + dto.getTipo() + " ya existe en el sistema");
        }

        Role entity = mapper.toEntity(dto);
        return mapper.toResponseDTO(repo.save(entity));
    }

    @Override
    @Transactional
    public RoleResponseDTO update(Long id, RoleUpdateDTO dto) {
        RoleResponseDTO response;
        Role entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));

        if (dto.getTipo() != null && !dto.getTipo().equals(entity.getTipo())) {
            if (repo.existsByTipo(dto.getTipo())) {
                throw new IllegalStateException("Ya existe otro rol con el tipo " + dto.getTipo());
            }
            entity.setTipo(dto.getTipo());
        }

        response = mapper.toResponseDTO(repo.save(entity));
        return response;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Role entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));

        if (!entity.getUsers().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar: Existen usuarios con este rol asignado");
        }

        repo.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponseDTO findById(Long id) {
        RoleResponseDTO response = repo.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponseDTO findByTipo(RoleType tipo) {
        RoleResponseDTO response = repo.findByTipo(tipo)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("El tipo de rol " + tipo + " no existe"));
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponseDTO> findAll() {
        List<RoleResponseDTO> response = repo.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
        return response;
    }
}
