package com.imo.cemetery.service.implementacionService;

import com.imo.cemetery.model.dto.implementacionServicio.ImplementacionServicioCreateDTO;
import com.imo.cemetery.model.dto.implementacionServicio.ImplementacionServicioResponseDTO;
import com.imo.cemetery.model.dto.implementacionServicio.ImplementacionServicioUpdateDTO;
import com.imo.cemetery.model.entity.Facturacion;
import com.imo.cemetery.model.entity.ImplementacionServicio;
import com.imo.cemetery.model.entity.Parcela;
import com.imo.cemetery.model.entity.Servicio;
import com.imo.cemetery.model.mapper.ImplementacionServicioMapper;
import com.imo.cemetery.repository.FacturacionRepository;
import com.imo.cemetery.repository.ImplementacionServicioRepository;
import com.imo.cemetery.repository.ParcelaRepository;
import com.imo.cemetery.repository.ServicioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImplementacionServicioServiceImpl implements ImplementacionServicioService {

    private final ImplementacionServicioRepository repo;
    private final ParcelaRepository parcelaRepo;
    private final ServicioRepository servicioRepo;
    private final FacturacionRepository facturacionRepo;
    private final ImplementacionServicioMapper mapper;

    @Override
    @Transactional
    public ImplementacionServicioResponseDTO create(ImplementacionServicioCreateDTO dto) {
        Parcela parcela = parcelaRepo.findById(dto.getParcelaId())
                .orElseThrow(() -> new EntityNotFoundException("Parcela no encontrada"));

        Servicio servicio = servicioRepo.findById(dto.getServicioId())
                .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado"));

        Facturacion facturacion = facturacionRepo.findById(dto.getFacturacionId())
                .orElseThrow(() -> new EntityNotFoundException("Registro de facturación no encontrado"));

        ImplementacionServicio entity = mapper.toEntity(dto);
        entity.setParcela(parcela);
        entity.setServicio(servicio);
        entity.setFacturacion(facturacion);

        return mapper.toResponseDTO(repo.save(entity));
    }

    @Override
    @Transactional
    public ImplementacionServicioResponseDTO update(Long id, ImplementacionServicioUpdateDTO dto) {
        ImplementacionServicioResponseDTO response;
        ImplementacionServicio entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registro de implementación no encontrado"));

        if (dto.getFechaRealizacion() != null) entity.setFechaRealizacion(dto.getFechaRealizacion());

        if (dto.getParcelaId() != null) {
            Parcela p = parcelaRepo.findById(dto.getParcelaId())
                    .orElseThrow(() -> new EntityNotFoundException("Parcela no encontrada"));
            entity.setParcela(p);
        }

        if (dto.getServicioId() != null) {
            Servicio s = servicioRepo.findById(dto.getServicioId())
                    .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado"));
            entity.setServicio(s);
        }

        response = mapper.toResponseDTO(repo.save(entity));
        return response;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        ImplementacionServicio entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registro no encontrado"));
        repo.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public ImplementacionServicioResponseDTO findById(Long id) {
        ImplementacionServicioResponseDTO response = repo.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Registro no encontrado"));
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImplementacionServicioResponseDTO> findAllByParcela(Long parcelaId) {
        List<ImplementacionServicioResponseDTO> response = repo.findAllByParcelaId(parcelaId)
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImplementacionServicioResponseDTO> findAllByFechaRange(LocalDate inicio, LocalDate fin) {

        List<ImplementacionServicioResponseDTO> response = repo.findAll()
                .stream()
                .filter(i -> !i.getFechaRealizacion().isBefore(inicio) && !i.getFechaRealizacion().isAfter(fin))
                .map(mapper::toResponseDTO)
                .toList();
        return response;
    }
}