package com.imo.cemetery.service.users;

import com.imo.cemetery.model.dto.user.UserCreateDTO;
import com.imo.cemetery.model.dto.user.UserResponseDTO;
import com.imo.cemetery.model.entity.User;

import java.util.List;

public interface UserService {

    List<UserResponseDTO> findAll();

    User findById(Long id);

    User findByEmail(String email);

    UserResponseDTO create(UserCreateDTO dto);

    void deleteById(Long id);

}
