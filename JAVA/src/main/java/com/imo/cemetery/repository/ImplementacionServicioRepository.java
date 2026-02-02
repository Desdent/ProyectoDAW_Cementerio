package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.ImplementacionServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * The interface Implementacion servicio repository.
 */
@Repository
public interface ImplementacionServicioRepository extends JpaRepository<ImplementacionServicio, Long> {

    /**
     * Find all by fecha realizacion list.
     *
     * @param fechaRealizacion the fecha realizacion
     * @return the list
     */
    List<ImplementacionServicio> findAllByFechaRealizacion(LocalDate fechaRealizacion);

    /**
     * Find all by parcela id list.
     *
     * @param id the id
     * @return the list
     */
    List<ImplementacionServicio> findAllByParcelaId(Long id);

    /**
     * Find all by servicio id list.
     *
     * @param id the id
     * @return the list
     */
    List<ImplementacionServicio> findAllByServicioId(Long id);

    /**
     * Find all by facturacion id list.
     *
     * @param id the id
     * @return the list
     */
    List<ImplementacionServicio> findAllByFacturacionId(Long id);

}
