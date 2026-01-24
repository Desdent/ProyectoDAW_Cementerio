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

    //Buscador
    List<Ayuntamiento> findAllByNombreContainingIgnoreCaseOrTelefonoContaining(String term);

    // Localización
    List<Ayuntamiento> findAllByCiudadProvinciaNombreIgnoreCase(String nombre);
    List<Ayuntamiento> findAllByCiudadProvinciaId(Long id);
    Optional<Ayuntamiento> findByCiudadByNombreIgnoreCase(String nombre);
    Optional<Ayuntamiento> findByCiudadId(Long id);

    boolean existsByEmail(String email);


}
