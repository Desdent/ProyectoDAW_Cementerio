package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.Ayuntamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Ayuntamiento repository.
 */
@Repository
public interface AyuntamientoRepository extends JpaRepository<Ayuntamiento, Long> {


    /**
     * Find by nif optional.
     *
     * @param nif the nif
     * @return the optional
     */
    Optional<Ayuntamiento> findByNif(String nif);

    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     */
    Optional<Ayuntamiento> findByEmail(String email);

    /**
     * Find all by nombre containing ignore case or telefono containing list.
     *
     * @param term     the term
     * @param telefono the telefono
     * @return the list
     */
//Buscador
    List<Ayuntamiento> findAllByNombreContainingIgnoreCaseOrTelefonoContaining(String term, String telefono);

    /**
     * Find all by ciudad provincia nombre ignore case list.
     *
     * @param nombre the nombre
     * @return the list
     */
// Localización
    List<Ayuntamiento> findAllByCiudadProvinciaNombreIgnoreCase(String nombre);

    /**
     * Find all by ciudad provincia id list.
     *
     * @param id the id
     * @return the list
     */
    List<Ayuntamiento> findAllByCiudadProvinciaId(Long id);

    /**
     * Find by ciudad nombre ignore case optional.
     *
     * @param nombre the nombre
     * @return the optional
     */
    Optional<Ayuntamiento> findByCiudadNombreIgnoreCase(String nombre);

    /**
     * Find by ciudad id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<Ayuntamiento> findByCiudadId(Long id);

    /**
     * Exists by email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    boolean existsByEmail(String email);


}
