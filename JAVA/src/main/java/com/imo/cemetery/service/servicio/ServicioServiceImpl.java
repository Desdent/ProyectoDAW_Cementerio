package com.imo.cemetery.service.servicio;

import com.imo.cemetery.model.dto.servicio.ServicioCreateDTO;
import com.imo.cemetery.model.dto.servicio.ServicioMapper;
import com.imo.cemetery.model.dto.servicio.ServicioResponseDTO;
import com.imo.cemetery.model.dto.servicio.ServicioUpdateDTO;
import com.imo.cemetery.model.entity.Servicio;
import com.imo.cemetery.model.enums.ServicioType;
import com.imo.cemetery.repository.ServicioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository repo;
    private final ServicioMapper mapper;

    @Override
    @Transactional
    public ServicioResponseDTO create(ServicioCreateDTO dto) {
        ServicioResponseDTO response;

        if (repo.existsByTipo(dto.getTipo())) {
            throw new IllegalStateException("El tipo de servicio " + dto.getTipo() + " ya está registrado en el sistema");
        }

        Servicio entity = mapper.toEntity(dto);
        response = mapper.toResponseDTO(repo.save(entity));

        return response;
    }

    @Override
    @Transactional
    public ServicioResponseDTO update(Long id, ServicioUpdateDTO dto) {
        ServicioResponseDTO response;
        Servicio entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado"));

        if (dto.getTipo() != null && !dto.getTipo().equals(entity.getTipo())) {
            if (repo.existsByTipo(dto.getTipo())) {
                throw new IllegalStateException("Ya existe otro servicio con el tipo " + dto.getTipo());
            }
            entity.setTipo(dto.getTipo());
        }

        response = mapper.toResponseDTO(repo.save(entity));
        return response;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Servicio entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado"));

        if (!entity.getDisponibilidadEnCementerios().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar: Existen tarifas asociadas a este servicio en diversos cementerios");
        }

        if (!entity.getImplementacionesServicios().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar: Este servicio ya ha sido ejecutado/implementado en parcelas");
        }

        repo.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public ServicioResponseDTO findById(Long id) {
        ServicioResponseDTO response = repo.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado"));
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ServicioResponseDTO findByTipo(ServicioType tipo) {
        ServicioResponseDTO response = repo.findByTipo(tipo)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("El tipo de servicio " + tipo + " no existe"));
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServicioResponseDTO> findAll() {
        List<ServicioResponseDTO> response = repo.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
        return response;
    }
}
