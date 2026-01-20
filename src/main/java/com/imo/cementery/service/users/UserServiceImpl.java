package com.imo.cementery.service.users;

import com.imo.cementery.model.dto.user.UserCreateDTO;
import com.imo.cementery.model.dto.user.UserResponseDTO;
import com.imo.cementery.model.entity.User;
import com.imo.cementery.model.mapper.UserMapper;
import com.imo.cementery.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final UserMapper userMapper;

    @Override
    public List<UserResponseDTO> findAll() {
        return this.repo.findAll().stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserResponseDTO> findById(Long id) {
        return this.repo.findById(id).map(userMapper::toResponseDTO);
    }

    @Override
    @Transactional // Si son metodos que vayan a escribir en la BBDD es recomendable esta anotación al parecer
    public UserResponseDTO create(UserCreateDTO dto) {
        // 1. Convertimos el DTO que viene del controlador a una Entidad
        User entity = userMapper.toEntity(dto);

        // 2. Guardamos la entidad en la base de datos
        // El repo nos devuelve la entidad guardada (ya con su ID generado)
        User savedUser = repo.save(entity);

        // 3. Convertimos la entidad guardada a un DTO de respuesta
        return userMapper.toResponseDTO(savedUser);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("No existe el ID: " + id);
        }
        repo.deleteById(id);
    }

}
