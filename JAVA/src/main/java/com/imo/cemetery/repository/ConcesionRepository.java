package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.Concesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConcesionRepository extends JpaRepository<Concesion, Long> {


    List<Concesion> findAllByParcelaId(Long id);
    List<Concesion> findAllByClienteId(Long clienteId);
    List<Concesion> findAllByClienteDniContainsIgnoreCase(String dni);

    List<Concesion> findAllByFechaInicio(LocalDate fechaInicio);
    List<Concesion> findAllByFechaFin(LocalDate fechaFin);
    List<Concesion> findAllByFechaFinBetween(LocalDate fecha1, LocalDate fecha2);
    List<Concesion> findAllByFechaFinBefore(LocalDate fecha);
    List<Concesion> findAllByFechaFinBeforeAndVencidaFalse(LocalDate today);
    List<Concesion> findAllByVencidaFalse();
    List<Concesion> findAllByVencidaTrue();
    Optional<Concesion> findByPagoId(Long id);
    List<Concesion> findAllByCementerioId(Long id);

}
