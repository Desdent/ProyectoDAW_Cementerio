package com.imo.cemetery.service.tarifaServicio;

import com.imo.cemetery.model.dto.tarifaServicio.TarifaServicioCreateDTO;
import com.imo.cemetery.model.dto.tarifaServicio.TarifaServicioResponseDTO;
import com.imo.cemetery.model.dto.tarifaServicio.TarifaServicioUpdateDTO;
import com.imo.cemetery.model.entity.Cementerio;
import com.imo.cemetery.model.entity.Servicio;
import com.imo.cemetery.model.entity.TarifaServicio;
import com.imo.cemetery.model.enums.ServicioType;
import com.imo.cemetery.model.mapper.TarifaServicioMapper;
import com.imo.cemetery.repository.CementerioRepository;
import com.imo.cemetery.repository.ServicioRepository;
import com.imo.cemetery.repository.TarifaServicioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TarifaServicioServiceImpl implements TarifaServicioService {

    private final TarifaServicioRepository repo;
    private final CementerioRepository cementerioRepo;
    private final ServicioRepository servicioRepo;
    private final TarifaServicioMapper mapper;

    @Override
    @Transactional
    public TarifaServicioResponseDTO create(TarifaServicioCreateDTO dto) {
        if (repo.existsByCementerioIdAndServicioId(dto.getCementerioId(), dto.getServicioId())) {
            throw new IllegalStateException("Ya existe un precio para este servicio en este cementerio");
        }

        Cementerio cementerio = cementerioRepo.findById(dto.getCementerioId())
                .orElseThrow(() -> new EntityNotFoundException("Cementerio no encontrado"));

        Servicio servicio = servicioRepo.findById(dto.getServicioId())
                .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado"));

        TarifaServicio entity = mapper.toEntity(dto);
        entity.setCementerio(cementerio);
        entity.setServicio(servicio);

        return mapper.toResponseDTO(repo.save(entity));
    }

    @Override
    @Transactional
    public TarifaServicioResponseDTO update(Long id, TarifaServicioUpdateDTO dto) {
        TarifaServicioResponseDTO response;
        TarifaServicio entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarifa no encontrada"));

        if (dto.getPrecio() != null) entity.setPrecio(dto.getPrecio());

        // Si se intenta cambiar el cementerio o el servicio, valida la nueva combinación
        if (dto.getCementerioId() != null || dto.getServicioId() != null) {
            Long cementerioId = (dto.getCementerioId() != null) ? dto.getCementerioId() : entity.getCementerio().getId();
            Long servicioId = (dto.getServicioId() != null) ? dto.getServicioId() : entity.getServicio().getId();

            if (!cementerioId.equals(entity.getCementerio().getId()) || !servicioId.equals(entity.getServicio().getId())) {
                if (repo.existsByCementerioIdAndServicioId(cementerioId, servicioId)) {
                    throw new IllegalStateException("La combinación de cementerio y servicio ya tiene un precio asignado");
                }

                Cementerio c = cementerioRepo.findById(cementerioId).orElseThrow(() -> new EntityNotFoundException("Cementerio no encontrado"));
                Servicio s = servicioRepo.findById(servicioId).orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado"));

                entity.setCementerio(c);
                entity.setServicio(s);
            }
        }

        response = mapper.toResponseDTO(repo.save(entity));
        return response;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        TarifaServicio entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarifa no encontrada"));
        repo.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public TarifaServicioResponseDTO findById(Long id) {
        TarifaServicioResponseDTO response = repo.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Tarifa no encontrada"));
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TarifaServicioResponseDTO> findAll() {
        List<TarifaServicioResponseDTO> response = repo.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TarifaServicioResponseDTO> findAllByCementerio(Long cementerioId) {
        List<TarifaServicioResponseDTO> response = repo.findAllByCementerioId(cementerioId)
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public TarifaServicioResponseDTO findPrecioServicio(Long cementerioId, ServicioType tipo) {
        TarifaServicioResponseDTO response = repo.findByServicioTipoAndCementerioId(tipo, cementerioId)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("No hay tarifa definida para " + tipo + " en este cementerio"));
        return response;
    }
}
