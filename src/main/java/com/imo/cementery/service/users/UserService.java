package com.imo.cementery.service.users;

import com.imo.cementery.model.dto.user.UserCreateDTO;
import com.imo.cementery.model.dto.user.UserResponseDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserResponseDTO> findAll();

    Optional<UserResponseDTO> findById(Long id);

    UserResponseDTO create(UserCreateDTO dto);

    void deleteById(Long id);

}
