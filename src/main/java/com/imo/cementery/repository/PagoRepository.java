package com.imo.cementery.repository;

import com.imo.cementery.model.entity.Pago;
import com.imo.cementery.model.enums.PagoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findAllByFecha(LocalDate fecha);

    List<Pago> findAllByMetodo(PagoType metodo);

    List<Pago> findAllByFechaAndMetodo(LocalDate fecha, PagoType metodo);

    // List<Pago> findAllByConcesionParcelaCementerioId(Long cementerioId);
    @Query("SELECT p FROM Pago p " +
            "JOIN p.concesion c " +
            "JOIN c.parcelas parc " +
            "WHERE parc.zona.cementerio.id = :cementerioId")
    List<Pago> findAllByCementerioId(@Param("cementerioId") Long cementerioId);
    // Para ver los pagos de un cementerio específico. Al parecer JPA se lia al usar Parcela cuando el elemento en pago es Parcelas y ser una lista

}
