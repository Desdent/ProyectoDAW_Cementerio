package com.imo.cementery.repository;

import com.imo.cementery.model.entity.ImplementacionServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ImplementacionServicioRepository extends JpaRepository<ImplementacionServicio, Long> {

    List<ImplementacionServicio> findAllByFechaRealizacion(LocalDate fechaRealizacion);

    List<ImplementacionServicio> findAllByParcelaId(Long id);

    List<ImplementacionServicio> findAllByServicioId(Long id);

    List<ImplementacionServicio> findAllByFacturacionId(Long id);

}
