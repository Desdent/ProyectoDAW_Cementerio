package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.Role;
import com.imo.cemetery.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Role repository.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Find by tipo optional.
     *
     * @param tipo the tipo
     * @return the optional
     */
    Optional<Role> findByTipo(RoleType tipo);

    /**
     * Exists by tipo boolean.
     *
     * @param tipo the tipo
     * @return the boolean
     */
    boolean existsByTipo(RoleType tipo);
}
