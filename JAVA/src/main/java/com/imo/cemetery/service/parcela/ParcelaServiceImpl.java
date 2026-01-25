package com.imo.cemetery.service.parcela;

import com.imo.cemetery.model.dto.parcela.ParcelaCreateDTO;
import com.imo.cemetery.model.dto.parcela.ParcelaResponseDTO;
import com.imo.cemetery.model.dto.parcela.ParcelaUpdateDTO;
import com.imo.cemetery.model.entity.Parcela;
import com.imo.cemetery.model.entity.Zona;
import com.imo.cemetery.model.enums.EstadoType;
import com.imo.cemetery.model.mapper.ParcelaMapper;
import com.imo.cemetery.repository.ParcelaRepository;
import com.imo.cemetery.repository.ZonaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParcelaServiceImpl implements ParcelaService {

    private final ParcelaRepository repo;
    private final ZonaRepository zonaRepository;
    private final ParcelaMapper parcelaMapper;

    @Override
    @Transactional
    public ParcelaResponseDTO create(ParcelaCreateDTO dto) {
        if (repo.existsByCoordenadaXAndCoordenadaY(dto.getCoordenadaX(), dto.getCoordenadaY())) {
            throw new IllegalStateException("Ya existe una parcela en esas coordenadas GPS");
        }

        if (repo.existsByFilaAndColumnaAndZonaId(dto.getFila(), dto.getColumna(), dto.getZonaId())) {
            throw new IllegalStateException("Esa posición (Fila/Columna) ya está ocupada en esta zona");
        }

        Zona zona = zonaRepository.findById(dto.getZonaId())
                .orElseThrow(() -> new EntityNotFoundException("Zona no encontrada"));

        Parcela entity = parcelaMapper.toEntity(dto);
        entity.setZona(zona);
        entity.setEstado(EstadoType.LIBRE);

        return parcelaMapper.toResponseDTO(repo.save(entity));
    }

    @Override
    @Transactional
    public ParcelaResponseDTO update(Long id, ParcelaUpdateDTO dto) {
        Parcela entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parcela no encontrada"));

        if (dto.getCoordenadaX() != null) entity.setCoordenadaX(dto.getCoordenadaX());
        if (dto.getCoordenadaY() != null) entity.setCoordenadaY(dto.getCoordenadaY());
        if (dto.getFila() != null) entity.setFila(dto.getFila());
        if (dto.getColumna() != null) entity.setColumna(dto.getColumna());
        if (dto.getEstado() != null) entity.setEstado(dto.getEstado());

        if (dto.getZonaId() != null) {
            Zona zona = zonaRepository.findById(dto.getZonaId())
                    .orElseThrow(() -> new EntityNotFoundException("Zona no encontrada"));
            entity.setZona(zona);
        }

        return parcelaMapper.toResponseDTO(repo.save(entity));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Parcela entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parcela no encontrada"));

        if (!entity.getDifuntos().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar: La parcela contiene difuntos");
        }

        if (entity.getConcesion() != null) {
            throw new IllegalStateException("No se puede eliminar: La parcela tiene una concesión activa");
        }

        if (!entity.getImplementacionesEnParcela().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar: Hay servicios vinculados a esta parcela");
        }

        repo.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public ParcelaResponseDTO findById(Long id) {
        ParcelaResponseDTO response = repo.findById(id)
                .map(parcelaMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Parcela no encontrada"));

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParcelaResponseDTO> findAll() {
        List<ParcelaResponseDTO> response = repo.findAll()
                .stream()
                .map(parcelaMapper::toResponseDTO)
                .toList();

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParcelaResponseDTO> findAllByZonaId(Long zonaId) {
        List<ParcelaResponseDTO> response = repo.findAllByZonaId(zonaId)
                .stream()
                .map(parcelaMapper::toResponseDTO)
                .toList();

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParcelaResponseDTO> findAllByCementerioId(Long cementerioId) {
        List<ParcelaResponseDTO> response = repo.findAllByZonaCementerioId(cementerioId)
                .stream()
                .map(parcelaMapper::toResponseDTO)
                .toList();

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParcelaResponseDTO> findAllLibresByZona(Long zonaId) {
        List<ParcelaResponseDTO> response = repo.findAllByZonaIdAndConcesionIsNull(zonaId)
                .stream()
                .filter(Parcela::isLibre)
                .map(parcelaMapper::toResponseDTO)
                .toList();

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ParcelaResponseDTO findByUbicacionCompleta(Double x, Double y, Integer fila, Integer columna) {
        ParcelaResponseDTO response = repo.findByCoordenadaXAndCoordenadaYAndFilaAndColumna(x, y, fila, columna)
                .map(parcelaMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Ubicación de parcela no registrada"));

        return response;
    }
}