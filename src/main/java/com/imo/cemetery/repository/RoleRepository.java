package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.Role;
import com.imo.cemetery.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByTipo(RoleType tipo);

    boolean existsByTipo(RoleType tipo);
}
