package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.User;
import com.imo.cemetery.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findAllByRoleTipo(RoleType role);

    boolean existsByEmail(String email);

}
