package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.Ayuntamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AyuntamientoRepository extends JpaRepository<Ayuntamiento, Long> {

    Optional<Ayuntamiento> findByNif(String nif);

    Optional<Ayuntamiento> findByEmail(String email);

    Optional<Ayuntamiento> findByNombre(String nombre);

    Optional<Ayuntamiento> findByTelefono(String telefono);

    List<Ayuntamiento> findAllByProvincia(String provincia);

    boolean existsByEmail(String email);


}
