package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.Parcela;
import com.imo.cemetery.model.enums.EstadoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParcelaRepository extends JpaRepository<Parcela, Long> {

    // Coordenadas
    List<Parcela> findAllByCoordenadaX(Double coordenadaX);
    List<Parcela> findAllByCoordenadaY(Double coordenadaY);
    List<Parcela> findAllByCoordenadaXAndCoordenadaY(Double coordenadaX, Double coordenadaY);

    // El buscador
    Optional<Parcela> findByCoordenadaXAndCoordenadaYAndFilaAndColumna(Double x, Double y, Integer fila, Integer columna);

    // Relaciones
    List<Parcela> findAllByConcesionId(Long id);
    List<Parcela> findAllByZonaId(Long id);
    List<Parcela> findAllByZonaCementerioId(Long cementerioId);

    // Estados y Disponibilidad
    List<Parcela> findAllByEstado(EstadoType estado);
    List<Parcela> findAllByZonaIdAndConcesionIsNull(Long zonaId);
    List<Parcela> findAllByZonaCementerioIdAndConcesionIsNull(Long cementerioId);

    // Validaciones
    boolean existsByCoordenadaXAndCoordenadaY(Double x, Double y);
    boolean existsByFilaAndColumnaAndZonaId(Integer fila, Integer columna, Long zonaId);

    long countByEstado(EstadoType estado);
}