package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Cliente repository.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     */
    Optional<Cliente> findByEmail(String email); // Al parecer spring es capaz de hacer el join hacia atrás por si mismo

    /**
     * Find by dni optional.
     *
     * @param dni the dni
     * @return the optional
     */
    Optional<Cliente> findByDni(String dni);

    /**
     * Find all by nombre containing ignore case or apellido 1 containing ignore case or apellido 2 containing ignore case or telefono containing list.
     *
     * @param nombre   the nombre
     * @param ap1      the ap 1
     * @param ap2      the ap 2
     * @param telefono the telefono
     * @return the list
     */
//Buscador
    List<Cliente> findAllByNombreContainingIgnoreCaseOrApellido1ContainingIgnoreCaseOrApellido2ContainingIgnoreCaseOrTelefonoContaining(String nombre, String ap1, String ap2, String telefono);

    /**
     * Find all distinct by concesiones parcelas zona cementerio ayuntamiento id list.
     *
     * @param id the id
     * @return the list
     */
    List<Cliente> findAllDistinctByConcesiones_Parcelas_Zona_Cementerio_Ayuntamiento_Id(Long id);

    /**
     * Find all distinct by concesiones parcelas zona cementerio id list.
     *
     * @param id the id
     * @return the list
     */
    List<Cliente> findAllDistinctByConcesiones_Parcelas_Zona_Cementerio_Id(Long id);

    /**
     * Find all by ciudad provincia nombre ignore case list.
     *
     * @param nombreProvincia the nombre provincia
     * @return the list
     */
// Localización
    List<Cliente> findAllByCiudadProvinciaNombreIgnoreCase(String nombreProvincia);

    /**
     * Find all by ciudad provincia id list.
     *
     * @param provinciaId the provincia id
     * @return the list
     */
    List<Cliente> findAllByCiudadProvinciaId(Long provinciaId);

    /**
     * Find all by ciudad id list.
     *
     * @param ciudadId the ciudad id
     * @return the list
     */
    List<Cliente> findAllByCiudadId(Long ciudadId);

    /**
     * Find all by ciudad nombre ignore case list.
     *
     * @param ciudadNombre the ciudad nombre
     * @return the list
     */
    List<Cliente> findAllByCiudadNombreIgnoreCase(String ciudadNombre);


    /**
     * Exists by email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    boolean existsByEmail(String email);

    /**
     * Exists by dni boolean.
     *
     * @param dni the dni
     * @return the boolean
     */
    boolean existsByDni(String dni);



}
