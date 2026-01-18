package com.imo.cementery.repository;

import com.imo.cementery.model.entity.Parcela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParcelaRepository extends JpaRepository<Parcela, Long> {

    List<Parcela> findAllByCoordenadaX(Double coordenadaX);
    List<Parcela> findAllByCoordenadaY(Double coordenadaY);
    List<Parcela> findAllByCoordenadaXAndCoordenadaY(Double coordenadaX, Double coordenadaY);
    Optional<Parcela> findByCoordenadaXAndCoordenadaYAndFilaAndColumna(Double coordenadaX, Double coordenadaY, Integer fila, Integer Ccolumna);
    List<Parcela> findAllByConcesionId(Long id);
    List<Parcela> findAllByZonaId(Long id);
    //Parcela -> Zona -> Cementerio -> Id
    List<Parcela> findAllByZonaCementerioId(Long cementerioId);
    // Busca parcelas donde la concesión sea null
    List<Parcela> findAllByZonaIdAndConcesionIsNull(Long zonaId);

}
