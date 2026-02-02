package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.Pago;
import com.imo.cemetery.model.enums.PagoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * The interface Pago repository.
 */
@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    /**
     * Find all by fecha list.
     *
     * @param fecha the fecha
     * @return the list
     */
    List<Pago> findAllByFecha(LocalDate fecha);

    /**
     * Find all by metodo list.
     *
     * @param metodo the metodo
     * @return the list
     */
    List<Pago> findAllByMetodo(PagoType metodo);

    /**
     * Find all by fecha and metodo list.
     *
     * @param fecha  the fecha
     * @param metodo the metodo
     * @return the list
     */
    List<Pago> findAllByFechaAndMetodo(LocalDate fecha, PagoType metodo);

    /**
     * Find all by cementerio id list.
     *
     * @param cementerioId the cementerio id
     * @return the list
     */
    @Query("SELECT p FROM Pago p " +
            "JOIN p.concesion c " +
            "JOIN c.parcelas parc " +
            "WHERE parc.zona.cementerio.id = :cementerioId")
    List<Pago> findAllByCementerioId(@Param("cementerioId") Long cementerioId);
    // Para ver los pagos de un cementerio específico. Al parecer JPA se lia al usar Parcela cuando el elemento en pago es Parcelas y ser una lista

}
