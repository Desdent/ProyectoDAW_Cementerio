package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.TarifaServicio;
import com.imo.cemetery.model.enums.ServicioType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TarifaServicioRepository extends JpaRepository<TarifaServicio, Long> {

    // Devuelve la tarifa específica (con su precio) para un tipo de servicio en un cementerio
    Optional<TarifaServicio> findByServicioTipoAndCementerioId(ServicioType tipo, Long cementerioId);

    List<TarifaServicio> findAllByPrecio(Double precio);

    List<TarifaServicio> findAllByCementerioId(Long id);

    List<TarifaServicio> findAllByServicioId(Long id);

}
