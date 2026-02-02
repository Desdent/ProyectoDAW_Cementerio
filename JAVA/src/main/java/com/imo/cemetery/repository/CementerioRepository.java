package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.Cementerio;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Cementerio repository.
 */
@Repository
public interface CementerioRepository extends JpaRepository<Cementerio, Long> {


    /**
     * Find all by ayuntamiento email list.
     *
     * @param email the email
     * @return the list
     */
// Para el findAllByLoggedAyuntamiento() del service
    List<Cementerio> findAllByAyuntamientoEmail(String email);

    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     */
    Optional<Cementerio> findByEmail(String email);

    /**
     * Find all by nombre containing ignore case or email containing ignore case or telefono containing list.
     *
     * @param nombre   the nombre
     * @param email    the email
     * @param telefono the telefono
     * @return the list
     */
// Buscador
    List<Cementerio> findAllByNombreContainingIgnoreCaseOrEmailContainingIgnoreCaseOrTelefonoContaining(String nombre, String email, String telefono);

    /**
     * Find all by ayuntamiento ciudad provincia nombre list.
     *
     * @param nombre the nombre
     * @return the list
     */
    List<Cementerio> findAllByAyuntamientoCiudadProvinciaNombre(String nombre);

    /**
     * Find all by ayuntamiento ciudad provincia id list.
     *
     * @param id the id
     * @return the list
     */
    List<Cementerio> findAllByAyuntamientoCiudadProvinciaId(Long id);

    /**
     * Find all by ayuntamiento ciudad nombre list.
     *
     * @param nombre the nombre
     * @return the list
     */
    List<Cementerio> findAllByAyuntamientoCiudadNombre(String nombre);

    /**
     * Find all by ayuntamiento ciudad id list.
     *
     * @param id the id
     * @return the list
     */
    List<Cementerio> findAllByAyuntamientoCiudadId(Long id);

    /**
     * Find all by ayuntamiento id list.
     *
     * @param id the id
     * @return the list
     */
    List<Cementerio> findAllByAyuntamientoId(Long id);

    /**
     * Count by ayuntamiento id long.
     *
     * @param id the id
     * @return the long
     */
    Long countByAyuntamientoId(Long id);

    /**
     * Find by concesion id optional.
     *
     * @param concesionId the concesion id
     * @return the optional
     */
    @Query("SELECT c FROM Cementerio c " +
            "JOIN c.zonas z " +
            "JOIN z.parcelas p " +
            "WHERE p.concesion.id = :concesionId")
    Optional<Cementerio> findByConcesionId(@Param("concesionId") Long concesionId);

}
