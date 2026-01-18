package com.imo.cementery.repository;

import com.imo.cementery.model.entity.Zona;
import com.imo.cementery.model.enums.ZonaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ZonaRepository extends JpaRepository<Zona, Long> {

    // Para listar todas las zonas de un cementerio
    List<Zona> findAllByCementerioId(Long cementerioId);

    // Para buscar una zona específica de un cementerio por su tipo/nombre
    Optional<Zona> findByTipoAndCementerioId(ZonaType tipo, Long cementerioId);

    // Para validar que no se duplique un tipo de zona en el mismo cementerio
    boolean existsByTipoAndCementerioId(ZonaType tipo, Long cementerioId);
}
