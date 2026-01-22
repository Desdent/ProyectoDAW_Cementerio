package com.imo.cemetery.service.cementerio;

import com.imo.cemetery.model.dto.cementerio.CementerioCreateDTO;
import com.imo.cemetery.model.dto.cementerio.CementerioResponseDTO;
import com.imo.cemetery.model.dto.cementerio.CementerioUpdateDTO;
import com.imo.cemetery.model.entity.Ayuntamiento;
import com.imo.cemetery.model.entity.Cementerio;
import com.imo.cemetery.model.mapper.CementerioMapper;
import com.imo.cemetery.repository.AyuntamientoRepository;
import com.imo.cemetery.repository.CementerioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CementerioServiceImpl implements CementerioService {

    private final CementerioRepository repo;
    private final AyuntamientoRepository ayuntamientoRepo; // Necesario para asignar el dueño
    private final CementerioMapper cementerioMapper;

    @Override
    public List<CementerioResponseDTO> findAll() {
        return repo.findAll().stream()
                .map(cementerioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CementerioResponseDTO> findById(Long id) {
        return repo.findById(id)
                .map(cementerioMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public CementerioResponseDTO create(CementerioCreateDTO dto) {
        // 1. Obtenemos el email del Ayuntamiento autenticado desde el Token/SecurityContext
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // 2. Buscamos la entidad Ayuntamiento completa en la base de datos
        Ayuntamiento ayuntamiento = ayuntamientoRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Ayuntamiento no encontrado con email: " + email));

        // 3. Mapeamos el DTO a la entidad Cementerio
        Cementerio entity = cementerioMapper.toEntity(dto);

        // 4. ASIGNAMOS EL DUEÑO: Aquí es donde aplicamos lo que discutimos
        // El cementerio ahora "sabe" a qué ayuntamiento pertenece
        entity.setAyuntamiento(ayuntamiento);

        // 5. Guardamos
        Cementerio entitySaved = repo.save(entity);

        return cementerioMapper.toResponseDTO(entitySaved);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("No existe el Cementerio con ID: " + id);
        }
        repo.deleteById(id);
    }

    @Override
    @Transactional
    public CementerioResponseDTO update(CementerioUpdateDTO dto, Long id) {
        Cementerio entity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el Cementerio con ID: " + id));

        // Actualizamos los campos (ubicación, nombre, etc.) desde el DTO
        cementerioMapper.updateEntityFromDTO(dto, entity);

        repo.save(entity);
        return cementerioMapper.toResponseDTO(entity);
    }


    @Override
    public Optional<CementerioResponseDTO> save(CementerioCreateDTO cementerio) {

        Cementerio entity = cementerioMapper.toEntity(cementerio);

        repo.save(entity);

        return Optional.ofNullable(cementerioMapper.toResponseDTO(entity));
    }
}