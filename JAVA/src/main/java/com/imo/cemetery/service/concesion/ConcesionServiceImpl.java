package com.imo.cemetery.service.concesion;

import com.imo.cemetery.model.dto.concesion.ConcesionCreateDTO;
import com.imo.cemetery.model.dto.concesion.ConcesionResponseDTO;
import com.imo.cemetery.model.dto.concesion.ConcesionUpdateDTO;
import com.imo.cemetery.model.entity.Cliente;
import com.imo.cemetery.model.entity.Concesion;
import com.imo.cemetery.model.entity.Parcela;
import com.imo.cemetery.model.enums.EstadoType;
import com.imo.cemetery.model.mapper.ConcesionMapper;
import com.imo.cemetery.repository.ClienteRepository;
import com.imo.cemetery.repository.ConcesionRepository;
import com.imo.cemetery.repository.ParcelaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcesionServiceImpl implements ConcesionService{

    private ConcesionRepository repo;
    private ClienteRepository clienteRepository;
    private ConcesionMapper concesionMapper;
    private ParcelaRepository parcelaRepository;


    // CRUD

    @Override
    @Transactional
    public ConcesionResponseDTO create(ConcesionCreateDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new AccessDeniedException("Usuario no autenticado");
        }

        Cliente cliente;
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE"))) {
            cliente = clienteRepository.findByEmail(auth.getName())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        } else {
            cliente = clienteRepository.findById(dto.getClienteId())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        }

        List<Parcela> parcelas = parcelaRepository.findAllById(dto.getParcelaIds());
        if (parcelas.size() != dto.getParcelaIds().size()) {
            throw new EntityNotFoundException("Una o más parcelas no existen");
        }

        if (parcelas.stream().anyMatch(p -> !p.isLibre())) {
            throw new IllegalStateException("Alguna de las parcelas ya está ocupada");
        }

        Concesion entity = concesionMapper.toEntity(dto);
        entity.setCliente(cliente);
        entity.setVencida(false);

        final Concesion savedEntity = repo.save(entity);

        parcelas.forEach(p -> {
            p.setConcesion(savedEntity);
            p.setEstado(EstadoType.RESERVADA);
        });
        parcelaRepository.saveAll(parcelas);

        return concesionMapper.toResponseDTO(savedEntity);
    }

    @Override
    @Transactional
    public ConcesionResponseDTO update(ConcesionUpdateDTO dto, Long id) {
        Concesion entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Concesión no encontrada"));

        if (dto.getPrecio() != null) entity.setPrecio(dto.getPrecio());
        if (dto.getFechaFin() != null) entity.setFechaFin(dto.getFechaFin());
        if (dto.getVencida() != null) entity.setVencida(dto.getVencida());

        return concesionMapper.toResponseDTO(repo.save(entity));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Concesion entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Concesión no encontrada"));

        List<Parcela> parcelas = entity.getParcelas();
        if (parcelas != null) {
            parcelas.forEach(p -> {
                p.setConcesion(null);
                p.setEstado(EstadoType.LIBRE);
            });
            parcelaRepository.saveAll(parcelas);
        }

        repo.delete(entity);
    }


    // Consultas

    @Override
    public List<ConcesionResponseDTO> findAllByParcelaId(Long id) {
        return List.of();
    }

    @Override
    public List<ConcesionResponseDTO> findAllByClienteId(Long id) {
        return List.of();
    }

    @Override
    public List<ConcesionResponseDTO> findAllByVencida(boolean vencida) {
        return List.of();
    }

    @Override
    public List<ConcesionResponseDTO> findAllActivas() {
        return List.of();
    }

    @Override
    public List<ConcesionResponseDTO> findAllByFechaFinBefore(LocalDate fecha) {
        return List.of();
    }

    @Override
    public List<ConcesionResponseDTO> findAllByFechaFinBetween(LocalDate fecha1, LocalDate fecha2) {
        return List.of();
    }

    @Override
    public List<ConcesionResponseDTO> findAllCasiVencidas() {
        return List.of();
    }

    @Override
    public ConcesionResponseDTO findAllByPagoId(Long id) {
        return null;
    }

    @Override
    public List<ConcesionResponseDTO> findAllByCementerioId(Long id) {
        return List.of();
    }
}
