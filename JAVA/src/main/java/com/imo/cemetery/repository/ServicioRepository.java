package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.Servicio;
import com.imo.cemetery.model.enums.ServicioType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Servicio repository.
 */
@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    /**
     * Find by tipo optional.
     *
     * @param tipo the tipo
     * @return the optional
     */
// Para buscar el servicio global por su tipo
    Optional<Servicio> findByTipo(ServicioType tipo);

    /**
     * Find all distinct by disponibilidad en cementerios cementerio id list.
     *
     * @param id the id
     * @return the list
     */
    List<Servicio> findAllDistinctByDisponibilidadEnCementerios_CementerioId(Long id);

    /**
     * Find all distinct by disponibilidad en cementerios cementerio ayuntamiento id list.
     *
     * @param id the id
     * @return the list
     */
    List<Servicio> findAllDistinctByDisponibilidadEnCementerios_Cementerio_AyuntamientoId(Long id);

    /**
     * Exists by tipo boolean.
     *
     * @param tipo the tipo
     * @return the boolean
     */
// Para comprobar si el tipo de servicio existe en el sistema
    boolean existsByTipo(ServicioType tipo);
}
