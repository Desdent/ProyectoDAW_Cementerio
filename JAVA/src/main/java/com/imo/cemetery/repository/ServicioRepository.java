package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.Servicio;
import com.imo.cemetery.model.enums.ServicioType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    // Para buscar el servicio global por su tipo
    Optional<Servicio> findByTipo(ServicioType tipo);
    List<Servicio> findAllDistinctByDisponibilidadEnCementerios_CementerioId(Long id);
    List<Servicio> findAllDistinctByDisponibilidadEnCementerios_Cementerio_AyuntamientoId(Long id);

    // Para comprobar si el tipo de servicio existe en el sistema
    boolean existsByTipo(ServicioType tipo);
}
