package com.imo.cemetery.service.provincia;

import com.imo.cemetery.model.entity.Provincia;
import com.imo.cemetery.model.mapper.ProvinciaMapper;
import com.imo.cemetery.repository.ProvinciaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.imo.cemetery.model.dto.provincia.ProvinciaResponseDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProvinciaServiceImpl implements ProvinciaService {

    private final ProvinciaRepository repository;
    private final ProvinciaMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProvinciaResponseDTO> getAll() {
        return mapper.toResponseDTOList(repository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public ProvinciaResponseDTO getById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Provincia no encontrada con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Provincia getEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error interno: Provincia con ID " + id + " no existe"));
    }

    @Override
    @Transactional
    public ProvinciaResponseDTO create(String nombre, Long id) {
        if (repository.existsById(id)) {
            return mapper.toResponseDTO(repository.getReferenceById(id));
        }
        Provincia p = new Provincia();
        p.setId(id);
        p.setNombre(nombre);
        return mapper.toResponseDTO(repository.save(p));
    }
}