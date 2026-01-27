package com.imo.cemetery.service.ayuntamiento;

import com.imo.cemetery.model.dto.ayuntamiento.AyuntamientoCreateDTO;
import com.imo.cemetery.model.dto.ayuntamiento.AyuntamientoResponseDTO;
import com.imo.cemetery.model.dto.ayuntamiento.AyuntamientoUpdateDTO;
import com.imo.cemetery.model.entity.Ayuntamiento;
import com.imo.cemetery.model.entity.Role;
import com.imo.cemetery.model.enums.RoleType;
import com.imo.cemetery.model.mapper.AyuntamientoMapper;
import com.imo.cemetery.repository.AyuntamientoRepository;
import com.imo.cemetery.repository.CiudadRepository;
import com.imo.cemetery.repository.RoleRepository;
import com.imo.cemetery.service.ciudad.CiudadService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AyuntamientoServiceImpl implements AyuntamientoService {

    private final AyuntamientoRepository repo;
    private final RoleRepository roleRepo;
    private final AyuntamientoMapper ayuntamientoMapper;
    private final PasswordEncoder passwordEncoder;
    private final CiudadService ciudadService;
    private final CiudadRepository ciudadRepo;


    //CRUD
    @Override
    @Transactional
    public AyuntamientoResponseDTO create(AyuntamientoCreateDTO dto) {
        Ayuntamiento entity = ayuntamientoMapper.toEntity(dto);

        Role role = roleRepo.findByTipo(RoleType.ROLE_AYUNTAMIENTO)
                .orElseThrow(() -> new EntityNotFoundException("Rol ROLE_AYUNTAMIENTO no encontrado"));
        entity.setRole(role);

        entity.setCiudad(ciudadRepo.getReferenceById(dto.getCiudadId()));

        entity.setPassword(passwordEncoder.encode(dto.getPassword()));

        Ayuntamiento entitySaved = repo.save(entity);

        return ayuntamientoMapper.toResponseDTO(entitySaved);
    }

    @Override
    @Transactional
    public AyuntamientoResponseDTO update(AyuntamientoUpdateDTO dto, Long id) {

        Ayuntamiento entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe el Ayuntamiento con ID: " + id));

        ayuntamientoMapper.updateEntityFromDTO(dto, entity);

        repo.save(entity);

        return ayuntamientoMapper.toResponseDTO(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!repo.existsById(id)) {
            // #TODO: Cambiar por ResourceNotFoundException cuando tengas el paquete exception
            throw new EntityNotFoundException("No existe el Ayuntamiento con ID: " + id);
        }
        repo.deleteById(id);
    }


    // Consultas

    @Override
    public AyuntamientoResponseDTO findById(Long id) {
        return repo.findById(id)
                .map(ayuntamientoMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("No existe el ayuntamiento con ID: " + id));
    }

    @Override
    public AyuntamientoResponseDTO findByNif(String nif) {
        return repo.findByNif(nif)
                .map(ayuntamientoMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("No existe el ayuntamiento con nif: " + nif));
    }

    @Override
    public List<AyuntamientoResponseDTO> findAll() {
        return repo.findAll().stream()
                .map(ayuntamientoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AyuntamientoResponseDTO findByEmail(String email) {
        return repo.findByEmail(email)
                .map(ayuntamientoMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("No existe el ayuntamiento con email: " + email));
    }



    // Búsquedas y filtros

    @Override
    // La idea de este metodo es que se aplique cada vez que el usuario teclea algo en el buscador
    public List<AyuntamientoResponseDTO> findAllBySearchingTerm(String term){
        return repo.findAllByNombreContainingIgnoreCaseOrTelefonoContaining(term, term)
                .stream()
                .map(ayuntamientoMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<AyuntamientoResponseDTO> findAllByProvinciaNombre(String nombre) {
        return repo.findAllByCiudadProvinciaNombreIgnoreCase(nombre)
                .stream()
                .map(ayuntamientoMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<AyuntamientoResponseDTO> findAllByProvinciaId(Long id) {
        return repo.findAllByCiudadProvinciaId(id)
                .stream()
                .map(ayuntamientoMapper::toResponseDTO)
                .toList();
    }

    @Override
    public AyuntamientoResponseDTO findByCiudadNombre(String nombre) {
        return repo.findByCiudadNombreIgnoreCase(nombre)
                .map(ayuntamientoMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("No existe ayuntamiento en la  ciudad con nombre: " + nombre));
    }

    @Override
    public AyuntamientoResponseDTO findByCiudadId(Long id) {
        return repo.findByCiudadId(id)
                .map(ayuntamientoMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("No existe ayuntamiento en la ciudad con ID: " + id));
    }



    @Override
    public boolean existsByEmail(String email) {
        return repo.existsByEmail(email);
    }
}
