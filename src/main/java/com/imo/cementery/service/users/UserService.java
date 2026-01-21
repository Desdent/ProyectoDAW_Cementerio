package com.imo.cementery.service.users;

import com.imo.cementery.model.dto.user.UserCreateDTO;
import com.imo.cementery.model.dto.user.UserResponseDTO;
import com.imo.cementery.model.entity.User;

import java.util.List;

public interface UserService {

    List<UserResponseDTO> findAll();

    User findById(Long id);

    User findByEmail(String email);

    UserResponseDTO create(UserCreateDTO dto);

    void deleteById(Long id);

}
