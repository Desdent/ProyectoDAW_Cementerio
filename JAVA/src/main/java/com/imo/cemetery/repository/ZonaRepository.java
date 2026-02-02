package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.Zona;
import com.imo.cemetery.model.enums.ZonaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Zona repository.
 */
@Repository
public interface ZonaRepository extends JpaRepository<Zona, Long> {

    /**
     * Find all by cementerio id list.
     *
     * @param cementerioId the cementerio id
     * @return the list
     */
    List<Zona> findAllByCementerioId(Long cementerioId);

    /**
     * Find all by nombre containing ignore case list.
     *
     * @param nombre the nombre
     * @return the list
     */
    List<Zona> findAllByNombreContainingIgnoreCase(String nombre);

    /**
     * Find all by tipo list.
     *
     * @param tipo the tipo
     * @return the list
     */
    List<Zona> findAllByTipo(ZonaType tipo);

    /**
     * Find by nombre containing ignore case and cementerio id list.
     *
     * @param nombre       the nombre
     * @param cementerioId the cementerio id
     * @return the list
     */
    List<Zona> findByNombreContainingIgnoreCaseAndCementerioId(String nombre, Long cementerioId);

    /**
     * Exists by tipo and cementerio id boolean.
     *
     * @param tipo         the tipo
     * @param cementerioId the cementerio id
     * @return the boolean
     */
    boolean existsByTipoAndCementerioId(ZonaType tipo, Long cementerioId);

    /**
     * Exists by nombre and cementerio id boolean.
     *
     * @param nombre       the nombre
     * @param cementerioId the cementerio id
     * @return the boolean
     */
    boolean existsByNombreAndCementerioId(String nombre, Long cementerioId);
}
