package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.Cementerio;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CementerioRepository extends JpaRepository<Cementerio, Long> {


    // Para el findAllByLoggedAyuntamiento() del service
    List<Cementerio> findAllByAyuntamientoEmail(String email);
    Optional<Cementerio> findByEmail(String email);

    // Buscador
    List<Cementerio> findAllByNombreContainingIgnoreCaseOrEmailContainingIgnoreCaseOrTelefonoContaining(String nombre, String email, String telefono);
    List<Cementerio> findAllByAyuntamientoCiudadProvinciaNombre(String nombre);
    List<Cementerio> findAllByAyuntamientoCiudadProvinciaId(Long id);
    List<Cementerio> findAllByAyuntamientoCiudadNombre(String nombre);
    List<Cementerio> findAllByAyuntamientoCiudadId(Long id);
    List<Cementerio> findAllByAyuntamientoId(Long id);
    Long countByAyuntamientoId(Long id);

}
