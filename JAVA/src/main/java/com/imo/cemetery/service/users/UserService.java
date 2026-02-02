package com.imo.cemetery.service.users;

import com.imo.cemetery.model.dto.user.UserCreateDTO;
import com.imo.cemetery.model.dto.user.UserResponseDTO;
import com.imo.cemetery.model.entity.User;

import java.util.List;

/**
 * The interface User service.
 */
public interface UserService {

    /**
     * Find all list.
     *
     * @return the list
     */
    List<UserResponseDTO> findAll();

    /**
     * Find by id user.
     *
     * @param id the id
     * @return the user
     */
    User findById(Long id);

    /**
     * Find by email user.
     *
     * @param email the email
     * @return the user
     */
    User findByEmail(String email);

    /**
     * Create user response dto.
     *
     * @param dto the dto
     * @return the user response dto
     */
    UserResponseDTO create(UserCreateDTO dto);

    /**
     * Delete by id.
     *
     * @param id the id
     */
    void deleteById(Long id);

}
