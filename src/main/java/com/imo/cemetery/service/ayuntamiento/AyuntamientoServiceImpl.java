package com.imo.cemetery.service.ayuntamiento;

import com.imo.cemetery.model.dto.ayuntamiento.AyuntamientoCreateDTO;
import com.imo.cemetery.model.dto.ayuntamiento.AyuntamientoResponseDTO;
import com.imo.cemetery.model.dto.ayuntamiento.AyuntamientoUpdateDTO;
import com.imo.cemetery.model.entity.Ayuntamiento;
import com.imo.cemetery.model.mapper.AyuntamientoMapper;
import com.imo.cemetery.repository.AyuntamientoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AyuntamientoServiceImpl implements AyuntamientoService {

    private final AyuntamientoRepository repo;
    private final AyuntamientoMapper ayuntamientoMapper;

    @Override
    public List<AyuntamientoResponseDTO> findAll() {
        return repo.findAll().stream()
                .map(ayuntamientoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AyuntamientoResponseDTO> findById(Long id) {
        return repo.findById(id)
                .map(ayuntamientoMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public AyuntamientoResponseDTO create(AyuntamientoCreateDTO dto) {

        Ayuntamiento entity = ayuntamientoMapper.toEntity(dto);

        Ayuntamiento entitySaved = repo.save(entity);

        return ayuntamientoMapper.toResponseDTO(entitySaved);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!repo.existsById(id)) {
            // #TODO: Cambiar por ResourceNotFoundException cuando tengas el paquete exception
            throw new RuntimeException("No existe el Ayuntamiento con ID: " + id);
        }
        repo.deleteById(id);
    }

    @Override
    @Transactional
    public AyuntamientoResponseDTO update(AyuntamientoUpdateDTO dto, Long id) {

        Ayuntamiento entity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el Ayuntamiento con ID: " + id));

        ayuntamientoMapper.updateEntityFromDTO(dto, entity);

        repo.save(entity);

        return ayuntamientoMapper.toResponseDTO(entity);
    }
}
