package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.TarifaServicio;
import com.imo.cemetery.model.enums.ServicioType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * The interface Tarifa servicio repository.
 */
@Repository
public interface TarifaServicioRepository extends JpaRepository<TarifaServicio, Long> {

    /**
     * Find by servicio tipo and cementerio id optional.
     *
     * @param tipo         the tipo
     * @param cementerioId the cementerio id
     * @return the optional
     */
    Optional<TarifaServicio> findByServicioTipoAndCementerioId(ServicioType tipo, Long cementerioId);

    /**
     * Find all by precio list.
     *
     * @param precio the precio
     * @return the list
     */
    List<TarifaServicio> findAllByPrecio(BigDecimal precio);

    /**
     * Find all by cementerio id list.
     *
     * @param id the id
     * @return the list
     */
    List<TarifaServicio> findAllByCementerioId(Long id);

    /**
     * Find all by servicio id list.
     *
     * @param id the id
     * @return the list
     */
    List<TarifaServicio> findAllByServicioId(Long id);

    /**
     * Exists by cementerio id and servicio id boolean.
     *
     * @param cementerioId the cementerio id
     * @param servicioId   the servicio id
     * @return the boolean
     */
    boolean existsByCementerioIdAndServicioId(Long cementerioId, Long servicioId);
}
