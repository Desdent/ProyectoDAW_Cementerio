package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.Facturacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Facturacion repository.
 */
@Repository
public interface FacturacionRepository extends JpaRepository<Facturacion, Long> {

    /**
     * Find all by dni list.
     *
     * @param dni the dni
     * @return the list
     */
    List<Facturacion> findAllByDni(String dni);

    /**
     * Find all by nombre list.
     *
     * @param nombre the nombre
     * @return the list
     */
    List<Facturacion> findAllByNombre(String nombre);

    /**
     * Find all by apellido 1 list.
     *
     * @param apellido1 the apellido 1
     * @return the list
     */
    List<Facturacion> findAllByApellido1(String apellido1);

    /**
     * Find all by nombre and apellido 1 list.
     *
     * @param nombre    the nombre
     * @param apellido1 the apellido 1
     * @return the list
     */
    List<Facturacion> findAllByNombreAndApellido1(String nombre, String apellido1);

    /**
     * Find all by telefono list.
     *
     * @param telefono the telefono
     * @return the list
     */
    List<Facturacion> findAllByTelefono(String telefono);

    /**
     * Find by pago id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<Facturacion> findByPagoId(Long id);

}
