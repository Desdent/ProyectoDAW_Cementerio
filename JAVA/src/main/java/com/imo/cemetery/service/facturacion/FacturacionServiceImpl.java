package com.imo.cemetery.service.facturacion;

import com.imo.cemetery.model.dto.facturacion.FacturacionCreateDTO;
import com.imo.cemetery.model.dto.facturacion.FacturacionResponseDTO;
import com.imo.cemetery.model.dto.facturacion.FacturacionUpdateDTO;
import com.imo.cemetery.model.entity.Facturacion;
import com.imo.cemetery.model.entity.Pago;
import com.imo.cemetery.model.mapper.FacturacionMapper;
import com.imo.cemetery.repository.FacturacionRepository;
import com.imo.cemetery.repository.PagoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacturacionServiceImpl implements FacturacionService {

    private final FacturacionRepository repo;
    private final PagoRepository pagoRepo;
    private final FacturacionMapper mapper;

    @Override
    @Transactional
    public FacturacionResponseDTO create(FacturacionCreateDTO dto) {
        Pago pago = pagoRepo.findById(dto.getPagoId())
                .orElseThrow(() -> new EntityNotFoundException("Pago no encontrado"));

        if (pago.getImporte().compareTo(dto.getImporte()) != 0) {
            throw new IllegalStateException("El importe de la factura no coincide con el importe del pago realizado");
        }

        Facturacion entity = mapper.toEntity(dto);
        entity.setPago(pago);

        return mapper.toResponseDTO(repo.save(entity));
    }

    @Override
    @Transactional
    public FacturacionResponseDTO update(Long id, FacturacionUpdateDTO dto) {
        FacturacionResponseDTO response;
        Facturacion entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Facturación no encontrada"));

        if (dto.getNombre() != null) entity.setNombre(dto.getNombre());
        if (dto.getApellido1() != null) entity.setApellido1(dto.getApellido1());
        if (dto.getDireccion() != null) entity.setDireccion(dto.getDireccion());
        if (dto.getTelefono() != null) entity.setTelefono(dto.getTelefono());

        response = mapper.toResponseDTO(repo.save(entity));
        return response;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Facturacion entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Facturación no encontrada"));

        if (!entity.getImplementacionesServicios().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar una factura que tiene servicios vinculados");
        }

        repo.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public FacturacionResponseDTO findById(Long id) {
        FacturacionResponseDTO response = repo.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Facturación no encontrada"));
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FacturacionResponseDTO> findAll() {
        List<FacturacionResponseDTO> response = repo.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FacturacionResponseDTO> findAllByDni(String dni) {
        List<FacturacionResponseDTO> response = repo.findAllByDni(dni)
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public FacturacionResponseDTO findByPagoId(Long pagoId) {
        FacturacionResponseDTO response = repo.findByPagoId(pagoId)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("No existe factura para el pago especificado"));
        return response;
    }
}
