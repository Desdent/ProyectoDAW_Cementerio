package com.imo.cemetery.service.cementerio;

import com.imo.cemetery.model.dto.cementerio.CementerioCreateDTO;
import com.imo.cemetery.model.dto.cementerio.CementerioResponseDTO;
import com.imo.cemetery.model.dto.cementerio.CementerioUpdateDTO;
import com.imo.cemetery.model.entity.Ayuntamiento;
import com.imo.cemetery.model.entity.Cementerio;
import com.imo.cemetery.model.mapper.CementerioMapper;
import com.imo.cemetery.repository.AyuntamientoRepository;
import com.imo.cemetery.repository.CementerioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CementerioServiceImpl implements CementerioService {

    private final CementerioRepository repo;
    private final AyuntamientoRepository ayuntamientoRepo; // Necesario para asignar el dueño
    private final CementerioMapper cementerioMapper;


    // CRUD

    @Override
    @Transactional
    public CementerioResponseDTO create(CementerioCreateDTO dto) {

        // Obtenemos la autenticación de forma segura
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) throw new AccessDeniedException("Usuario no identificado");

        // Obtenemos el email del Ayuntamiento autenticado desde el Token/SecurityContext
        String email = auth.getName();


        // Buscamos el Ayuntamiento completo en la base de datos
        Ayuntamiento ayuntamiento = ayuntamientoRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Ayuntamiento no encontrado con email: " + email));

        // Mapeamos el DTO a la entidad Cementerio
        Cementerio entity = cementerioMapper.toEntity(dto);

        // Asignamos el dueño
        // El cementerio ahora sae a qué ayuntamiento pertenece
        entity.setAyuntamiento(ayuntamiento);

        // 5. Guardamos
        Cementerio entitySaved = repo.save(entity);

        return cementerioMapper.toResponseDTO(entitySaved);
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
    @Transactional
    public void deleteById(Long id)
    {
        if(!repo.existsById(id))
        {
            throw new EntityNotFoundException("No existe cementerio con ID: " + id);
        }

        repo.deleteById(id);
    }


    // Consultas

    @Override
    public List<CementerioResponseDTO> findAll() {
        return repo.findAll().stream()
                .map(cementerioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CementerioResponseDTO findById(Long id) {
        return repo.findById(id)
                .map(cementerioMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("No existe cementerio con ID: " + id));
    }

    @Override
    public CementerioResponseDTO findByEmail(String email) {
        return repo.findByEmail(email)
                .map(cementerioMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("No existe cementerio con email: " + email));
    }

    @Override
    public List<CementerioResponseDTO> findAllByLoggedAyuntamiento() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) throw new AccessDeniedException("Usuario no identificado.");

        String email = auth.getName();

        return repo.findAllByAyuntamientoEmail(email)
                .stream()
                .map(cementerioMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<CementerioResponseDTO> findAllByAyuntamientoEmail(String email)
    {
        return repo.findAllByAyuntamientoEmail(email)
                .stream()
                .map(cementerioMapper::toResponseDTO)
                .toList();
    }



    // Búsquedas y filtros

    @Override
    public List<CementerioResponseDTO> findAllBySearchingTerm(String term) {
        return repo.findAllByNombreContainingIgnoreCaseOrEmailContainingIgnoreCaseOrTelefonoContaining(term, term, term)
                .stream()
                .map(cementerioMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<CementerioResponseDTO> findAllByProvinciaNombre(String nombre) {
        return repo.findAllByAyuntamientoCiudadProvinciaNombre(nombre)
                .stream()
                .map(cementerioMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<CementerioResponseDTO> findAllByProvinciaId(Long id) {
        return repo.findAllByAyuntamientoCiudadProvinciaId(id)
                .stream()
                .map(cementerioMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<CementerioResponseDTO> findAllByCiudadNombre(String nombre) {
        return repo.findAllByAyuntamientoCiudadNombre(nombre)
                .stream()
                .map(cementerioMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<CementerioResponseDTO> findALlByCiudadId(Long id) {
        return repo.findAllByAyuntamientoCiudadId(id)
                .stream()
                .map(cementerioMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<CementerioResponseDTO> findAllByAyuntamientoId(Long id) {
        return repo.findAllByAyuntamientoId(id)
                .stream()
                .map(cementerioMapper::toResponseDTO)
                .toList();
    }

    @Override
    public Long countByAyuntamientoId(Long id) {
        return repo.countByAyuntamientoId(id);
    }


}