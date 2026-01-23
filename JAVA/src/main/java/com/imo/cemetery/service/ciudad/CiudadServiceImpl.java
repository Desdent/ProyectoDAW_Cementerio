package com.imo.cemetery.service.ciudad;

import com.imo.cemetery.model.entity.Ciudad;
import com.imo.cemetery.model.mapper.CiudadMapper;
import com.imo.cemetery.repository.CiudadRepository;
import com.imo.cemetery.repository.ProvinciaRepository;
import com.imo.cemetery.service.provincia.ProvinciaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.imo.cemetery.model.dto.ciudad.CiudadResponseDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CiudadServiceImpl implements CiudadService {

    private final CiudadRepository repository;
    private final CiudadMapper mapper;
    private final ProvinciaService provinciaService;
    private final ProvinciaRepository provinciaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CiudadResponseDTO> getAll() {
        List<Ciudad> ciudades = repository.findAll();
        return mapper.toResponseDTOList(ciudades);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CiudadResponseDTO> getByProvincia(Long provinciaId) {
        List<Ciudad> ciudades = repository.findAllByProvinciaId(provinciaId);
        return mapper.toResponseDTOList(ciudades);
    }

    @Override
    @Transactional(readOnly = true)
    public CiudadResponseDTO getById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Ciudad no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public CiudadResponseDTO create(String nombre, Long id, Long provinciaId) {
        if (repository.existsById(id)) {
            return mapper.toResponseDTO(repository.getReferenceById(id));
        }


        provinciaService.getById(provinciaId);

        Ciudad c = new Ciudad();
        c.setId(id);
        c.setNombre(nombre);

        c.setProvincia(provinciaRepository.getReferenceById(provinciaId));

        return mapper.toResponseDTO(repository.save(c));
    }

}