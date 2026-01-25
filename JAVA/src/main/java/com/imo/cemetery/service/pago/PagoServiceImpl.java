package com.imo.cemetery.service.pago;

import com.imo.cemetery.model.dto.pago.PagoCreateDTO;
import com.imo.cemetery.model.dto.pago.PagoResponseDTO;
import com.imo.cemetery.model.dto.pago.PagoUpdateDTO;
import com.imo.cemetery.model.entity.Pago;
import com.imo.cemetery.model.mapper.PagoMapper;
import com.imo.cemetery.repository.PagoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements PagoService {

    private final PagoRepository repo;
    private final PagoMapper mapper;

    @Override
    @Transactional
    public PagoResponseDTO create(PagoCreateDTO dto) {
        Pago entity = mapper.toEntity(dto);

        if (entity.getFecha() == null) entity.setFecha(LocalDate.now());

        return mapper.toResponseDTO(repo.save(entity));
    }

    @Override
    @Transactional
    public PagoResponseDTO update(Long id, PagoUpdateDTO dto) {
        PagoResponseDTO response;
        Pago entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pago no encontrado"));

        if (dto.getMetodo() != null) {
            entity.setMetodo(dto.getMetodo());
        }

        response = mapper.toResponseDTO(repo.save(entity));
        return response;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Pago entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pago no encontrado"));


        if (entity.getConcesion() != null || entity.getFacturacion() != null) {
            throw new IllegalStateException("No se puede anular un pago vinculado a un contrato/factura. Anule primero el documento vinculado.");
        }

        repo.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PagoResponseDTO findById(Long id) {
        PagoResponseDTO response = repo.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Pago no encontrado"));
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoResponseDTO> findAll() {
        List<PagoResponseDTO> response = repo.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoResponseDTO> findAllByFecha(LocalDate fecha) {
        List<PagoResponseDTO> response = repo.findAllByFecha(fecha)
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoResponseDTO> findAllByCementerio(Long cementerioId) {
        List<PagoResponseDTO> response = repo.findAllByCementerioId(cementerioId)
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
        return response;
    }
}
