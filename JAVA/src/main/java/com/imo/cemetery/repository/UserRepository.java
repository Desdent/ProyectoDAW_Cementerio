package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.User;
import com.imo.cemetery.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     */
    Optional<User> findByEmail(String email);

    /**
     * Find by username optional.
     *
     * @param email the email
     * @return the optional
     */
    Optional<User> findByUsername(String email);

    /**
     * Find all by role tipo list.
     *
     * @param role the role
     * @return the list
     */
    List<User> findAllByRoleTipo(RoleType role);

    /**
     * Exists by email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    boolean existsByEmail(String email);

}
