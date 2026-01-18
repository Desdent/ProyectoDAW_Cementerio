package com.imo.cementery.repository;

import com.imo.cementery.model.Concesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConcesionRepository extends JpaRepository<Concesion, Long> {

    List<Concesion> findAllByFechaInicio(LocalDate fechaInicio);
    List<Concesion> findAllByFechaFin(LocalDate fechaFin);
    List<Concesion> findAllByVencida(boolean vencida);
    Optional<Concesion> findByPagoId(Long id);
    List<Concesion> findAllByClienteId(Long clienteId);

}
