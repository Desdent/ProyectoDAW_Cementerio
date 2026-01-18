package com.imo.cementery.repository;

import com.imo.cementery.model.enums.ServicioType;
import com.imo.cementery.model.entity.TarifaServicio;
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
