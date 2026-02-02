package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.Cementerio;
import com.imo.cemetery.model.entity.Concesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * The interface Concesion repository.
 */
@Repository
public interface ConcesionRepository extends JpaRepository<Concesion, Long> {


    /**
     * Find by parcelas id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<Concesion> findByParcelas_Id(Long id);

    /**
     * Find all by cliente id list.
     *
     * @param clienteId the cliente id
     * @return the list
     */
    List<Concesion> findAllByClienteId(Long clienteId);

    /**
     * Find all by cliente dni contains ignore case list.
     *
     * @param dni the dni
     * @return the list
     */
    List<Concesion> findAllByClienteDniContainsIgnoreCase(String dni);

    /**
     * Find all by fecha inicio list.
     *
     * @param fechaInicio the fecha inicio
     * @return the list
     */
    List<Concesion> findAllByFechaInicio(LocalDate fechaInicio);

    /**
     * Find all by fecha fin list.
     *
     * @param fechaFin the fecha fin
     * @return the list
     */
    List<Concesion> findAllByFechaFin(LocalDate fechaFin);

    /**
     * Find all by fecha fin between list.
     *
     * @param fecha1 the fecha 1
     * @param fecha2 the fecha 2
     * @return the list
     */
    List<Concesion> findAllByFechaFinBetween(LocalDate fecha1, LocalDate fecha2);

    /**
     * Find all by fecha fin before list.
     *
     * @param fecha the fecha
     * @return the list
     */
    List<Concesion> findAllByFechaFinBefore(LocalDate fecha);

    /**
     * Find all by fecha fin before and vencida false list.
     *
     * @param today the today
     * @return the list
     */
    List<Concesion> findAllByFechaFinBeforeAndVencidaFalse(LocalDate today);

    /**
     * Find all by vencida false list.
     *
     * @return the list
     */
    List<Concesion> findAllByVencidaFalse();

    /**
     * Find all by vencida true list.
     *
     * @return the list
     */
    List<Concesion> findAllByVencidaTrue();

    /**
     * Find by pago id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<Concesion> findByPagoId(Long id);

    /**
     * Find all by parcelas zona cementerio id list.
     *
     * @param id the id
     * @return the list
     */
    List<Concesion> findAllByParcelas_Zona_Cementerio_Id(Long id);

    /**
     * Find concesiones by cliente and ayuntamiento list.
     *
     * @param clienteId the cliente id
     * @param aytoId    the ayto id
     * @return the list
     */
    @Query("SELECT DISTINCT con FROM Concesion con " +
            "JOIN con.parcelas p " +
            "WHERE con.cliente.id = :clienteId " +
            "AND p.zona.cementerio.ayuntamiento.id = :aytoId")
    List<Concesion> findConcesionesByClienteAndAyuntamiento(
            @Param("clienteId") Long clienteId,
            @Param("aytoId") Long aytoId
    );

}
