package com.imo.cementery.repository;

import com.imo.cementery.model.entity.Servicio;
import com.imo.cementery.model.enums.ServicioType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    // Para buscar el servicio global por su tipo
    Optional<Servicio> findByTipo(ServicioType tipo);

    // Para comprobar si el tipo de servicio existe en el sistema
    boolean existsByTipo(ServicioType tipo);
}
