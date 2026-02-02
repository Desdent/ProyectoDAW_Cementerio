package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.Parcela;
import com.imo.cemetery.model.enums.EstadoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Parcela repository.
 */
@Repository
public interface ParcelaRepository extends JpaRepository<Parcela, Long> {


    /**
     * Find by fila and columna optional.
     *
     * @param fila    the fila
     * @param columna the columna
     * @return the optional
     */
// El buscador
    Optional<Parcela> findByFilaAndColumna(Integer fila, Integer columna);

    /**
     * Find all by concesion id list.
     *
     * @param id the id
     * @return the list
     */
// Relaciones
    List<Parcela> findAllByConcesionId(Long id);

    /**
     * Find all by zona id list.
     *
     * @param id the id
     * @return the list
     */
    List<Parcela> findAllByZonaId(Long id);

    /**
     * Find all by zona cementerio id list.
     *
     * @param cementerioId the cementerio id
     * @return the list
     */
    List<Parcela> findAllByZonaCementerioId(Long cementerioId);

    /**
     * Find all by estado list.
     *
     * @param estado the estado
     * @return the list
     */
// Estados y Disponibilidad
    List<Parcela> findAllByEstado(EstadoType estado);

    /**
     * Find all by zona id and concesion is null list.
     *
     * @param zonaId the zona id
     * @return the list
     */
    List<Parcela> findAllByZonaIdAndConcesionIsNull(Long zonaId);

    /**
     * Find all by zona cementerio id and concesion is null list.
     *
     * @param cementerioId the cementerio id
     * @return the list
     */
    List<Parcela> findAllByZonaCementerioIdAndConcesionIsNull(Long cementerioId);

    /**
     * Exists by fila and columna and zona id boolean.
     *
     * @param fila    the fila
     * @param columna the columna
     * @param zonaId  the zona id
     * @return the boolean
     */
// Validaciones
    boolean existsByFilaAndColumnaAndZonaId(Integer fila, Integer columna, Long zonaId);

    /**
     * Count by estado long.
     *
     * @param estado the estado
     * @return the long
     */
    long countByEstado(EstadoType estado);
}