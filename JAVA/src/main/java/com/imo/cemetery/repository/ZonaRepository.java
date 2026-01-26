package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.Zona;
import com.imo.cemetery.model.enums.ZonaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ZonaRepository extends JpaRepository<Zona, Long> {

    List<Zona> findAllByCementerioId(Long cementerioId);
    List<Zona> findAllByNombreContainingIgnoreCase(String nombre);
    List<Zona> findAllByTipo(ZonaType tipo);
    List<Zona> findByNombreContainingIgnoreCaseAndCementerioId(String nombre, Long cementerioId);
    boolean existsByTipoAndCementerioId(ZonaType tipo, Long cementerioId);
    boolean existsByNombreAndCementerioId(String nombre, Long cementerioId);
}
