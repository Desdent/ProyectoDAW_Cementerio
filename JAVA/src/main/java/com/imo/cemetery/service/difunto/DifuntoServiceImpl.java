package com.imo.cemetery.service.difunto;

import com.imo.cemetery.model.dto.difunto.DifuntoCreateDTO;
import com.imo.cemetery.model.dto.difunto.DifuntoResponseDTO;
import com.imo.cemetery.model.dto.difunto.DifuntoUpdateDTO;
import com.imo.cemetery.model.entity.Difunto;
import com.imo.cemetery.model.entity.Parcela;
import com.imo.cemetery.model.enums.EstadoType;
import com.imo.cemetery.model.mapper.DifuntoMapper;
import com.imo.cemetery.repository.DifuntoRepository;
import com.imo.cemetery.repository.ParcelaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DifuntoServiceImpl implements DifuntoService {

    private final DifuntoRepository repo;
    private final ParcelaRepository parcelaRepository;
    private final DifuntoMapper difuntoMapper;

    @Override
    @Transactional
    public DifuntoResponseDTO create(DifuntoCreateDTO dto) {
        Parcela parcela = parcelaRepository.findById(dto.getParcelaId())
                .orElseThrow(() -> new EntityNotFoundException("Parcela no encontrada"));

        Difunto entity = difuntoMapper.toEntity(dto);
        entity.setParcela(parcela);

        parcela.setEstado(EstadoType.OCUPADA);
        parcelaRepository.save(parcela);

        return difuntoMapper.toResponseDTO(repo.save(entity));
    }

    @Override
    @Transactional
    public DifuntoResponseDTO update(Long id, DifuntoUpdateDTO dto) {

        DifuntoResponseDTO response;

        Difunto entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Difunto no encontrado"));


        if (dto.getNombre() != null) entity.setNombre(dto.getNombre());
        if (dto.getApellido1() != null) entity.setApellido1(dto.getApellido1());
        if (dto.getApellido2() != null) entity.setApellido2(dto.getApellido2());
        if (dto.getYearNacimiento() != null) entity.setYearNacimiento(dto.getYearNacimiento());
        if (dto.getYearDefuncion() != null) entity.setYearDefuncion(dto.getYearDefuncion());
        if (dto.getFechaEntierro() != null) entity.setFechaEntierro(dto.getFechaEntierro());
        if (dto.getMensaje() != null) entity.setMensaje(dto.getMensaje());
        if (dto.getFoto() != null) entity.setFoto(dto.getFoto());


        if (dto.getParcelaId() != null && !dto.getParcelaId().equals(entity.getParcela().getId())) {
            Parcela nuevaParcela = parcelaRepository.findById(dto.getParcelaId())
                    .orElseThrow(() -> new EntityNotFoundException("Nueva parcela no encontrada"));


            entity.setParcela(nuevaParcela);
            nuevaParcela.setEstado(EstadoType.OCUPADA);
            parcelaRepository.save(nuevaParcela);
        }

        response = difuntoMapper.toResponseDTO(repo.save(entity));


        return response;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Difunto entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Difunto no encontrado"));

        repo.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public DifuntoResponseDTO findById(Long id) {
        DifuntoResponseDTO response = repo.findById(id)
                .map(difuntoMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Difunto no encontrado"));
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DifuntoResponseDTO> findAll() {
        List<DifuntoResponseDTO> response = repo.findAll()
                .stream()
                .map(difuntoMapper::toResponseDTO)
                .toList();
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DifuntoResponseDTO> findByNombreCompleto(String nombre, String ape1, String ape2) {
        List<DifuntoResponseDTO> response = repo.findByNombreAndApellido1AndApellido2(nombre, ape1, ape2)
                .stream()
                .map(difuntoMapper::toResponseDTO)
                .toList();
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DifuntoResponseDTO> findAllByParcela(Long parcelaId) {
        List<DifuntoResponseDTO> response = repo.findAllByParcelaId(parcelaId)
                .stream()
                .map(difuntoMapper::toResponseDTO)
                .toList();
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DifuntoResponseDTO> findAllByYearDefuncion(Year year) {
        List<DifuntoResponseDTO> response = repo.findAllByYearDefuncion(year)
                .stream()
                .map(difuntoMapper::toResponseDTO)
                .toList();
        return response;
    }
}