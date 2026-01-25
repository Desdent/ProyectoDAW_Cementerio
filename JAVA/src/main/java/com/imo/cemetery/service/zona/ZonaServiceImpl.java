package com.imo.cemetery.service.zona;

import com.imo.cemetery.model.dto.zona.ZonaCreateDTO;
import com.imo.cemetery.model.dto.zona.ZonaResponseDTO;
import com.imo.cemetery.model.dto.zona.ZonaUpdateDTO;
import com.imo.cemetery.model.entity.Cementerio;
import com.imo.cemetery.model.entity.Zona;
import com.imo.cemetery.model.mapper.ZonaMapper;
import com.imo.cemetery.repository.CementerioRepository;
import com.imo.cemetery.repository.ZonaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ZonaServiceImpl implements ZonaService {

    private final ZonaRepository repo;
    private final CementerioRepository cementerioRepository;
    private final ZonaMapper zonaMapper;

    @Override
    @Transactional
    public ZonaResponseDTO create(ZonaCreateDTO dto) {
        if (repo.existsByNombreAndCementerioId(dto.getNombre(), dto.getCementerioId())) {
            throw new IllegalStateException("Ya existe una zona llamada '" + dto.getNombre() + "' en este cementerio");
        }

        Cementerio cementerio = cementerioRepository.findById(dto.getCementerioId())
                .orElseThrow(() -> new EntityNotFoundException("Cementerio no encontrado"));

        Zona entity = zonaMapper.toEntity(dto);
        entity.setCementerio(cementerio);

        return zonaMapper.toResponseDTO(repo.save(entity));
    }

    @Override
    @Transactional
    public ZonaResponseDTO update(Long id, ZonaUpdateDTO dto) {
        ZonaResponseDTO response;
        Zona entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Zona no encontrada"));

        if (dto.getNombre() != null) {
            // Valida que el nuevo nombre no esté pillado por otra zona del mismo cementerio
            if (!dto.getNombre().equals(entity.getNombre()) &&
                    repo.existsByNombreAndCementerioId(dto.getNombre(), entity.getCementerio().getId())) {
                throw new IllegalStateException("El nombre '" + dto.getNombre() + "' ya está en uso en este cementerio");
            }
            entity.setNombre(dto.getNombre());
        }

        if (dto.getTipo() != null) entity.setTipo(dto.getTipo());

        if (dto.getCementerioId() != null && !dto.getCementerioId().equals(entity.getCementerio().getId())) {
            Cementerio nuevoCementerio = cementerioRepository.findById(dto.getCementerioId())
                    .orElseThrow(() -> new EntityNotFoundException("Nuevo cementerio no encontrado"));
            entity.setCementerio(nuevoCementerio);
        }

        response = zonaMapper.toResponseDTO(repo.save(entity));
        return response;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Zona entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Zona no encontrada"));

        if (!entity.getParcelas().isEmpty()) {
            throw new IllegalStateException("No se puede borrar una zona que todavía tiene parcelas");
        }

        repo.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public ZonaResponseDTO findById(Long id) {
        ZonaResponseDTO response = repo.findById(id)
                .map(zonaMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Zona no encontrada"));
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ZonaResponseDTO> findAll() {
        List<ZonaResponseDTO> response = repo.findAll()
                .stream()
                .map(zonaMapper::toResponseDTO)
                .toList();
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ZonaResponseDTO> findAllByCementerioId(Long cementerioId) {
        List<ZonaResponseDTO> response = repo.findAllByCementerioId(cementerioId)
                .stream()
                .map(zonaMapper::toResponseDTO)
                .toList();
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ZonaResponseDTO> searchByNombre(String nombre) {
        List<ZonaResponseDTO> response = repo.findAllByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(zonaMapper::toResponseDTO)
                .toList();
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ZonaResponseDTO findByNombreEnCementerio(String nombre, Long cementerioId) {
        ZonaResponseDTO response = repo.findByNombreAndCementerioId(nombre, cementerioId)
                .map(zonaMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la zona '" + nombre + "' en este cementerio"));
        return response;
    }
}