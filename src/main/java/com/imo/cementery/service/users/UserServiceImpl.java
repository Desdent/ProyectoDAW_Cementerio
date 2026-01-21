package com.imo.cementery.service.users;

import com.imo.cementery.model.dto.user.UserCreateDTO;
import com.imo.cementery.model.dto.user.UserResponseDTO;
import com.imo.cementery.model.entity.User;
import com.imo.cementery.model.mapper.UserMapper;
import com.imo.cementery.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository repo;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponseDTO> findAll() {
        return this.repo.findAll().stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    // El proceso es el siguiente:
    // El méthod findAll del repo te devuelve una lista de objetos User -> El findAll del UserServicio espera devolver una lista de objetos UserResponseDTO, no de User ->
    // -> Por tanto, coge el flujo de informacion que devuleve el findALl del repo con stream -> Ese flujo lo mapea/convierte con map usando el userMapper de User a UserResponseDTO ->
    // -> una vez transformado, lo coge con collect y lo hace una lista con Collectors.toList, la cual esta vez es de objetos UserResponseDTO

    @Override
    public User findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new UsernameNotFoundException("ID no encontrada: " + id));
    }

    @Override
    public User findByEmail(String email) {
        return repo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email no encontrado: " + email));
    }

    @Override
    @Transactional // Si son metodos que vayan a escribir en la BBDD es recomendable esta anotación al parecer
    public UserResponseDTO create(UserCreateDTO dto) {
        // Se crea una entity
        User entity = userMapper.toEntity(dto);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        // Se guarda la entidad en la base de datos para poder usarla
        // El repo devuelve la entidad guardada (ya con su ID generado)
        User savedUser = repo.save(entity);

        // Se conviefrte a un responseDTO que es lo que espera devolver el method pasándole como parametro la entity guardada
        return userMapper.toResponseDTO(savedUser);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("No existe el ID: " + id); // #TODO cambiar por excepcion personaizada
        }
        repo.deleteById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }
}
