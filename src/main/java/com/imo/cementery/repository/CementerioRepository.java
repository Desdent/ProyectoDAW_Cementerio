package com.imo.cementery.repository;

import com.imo.cementery.model.entity.Cementerio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CementerioRepository extends JpaRepository<Cementerio, Long> {

    Optional<Cementerio> findByNombre(String nombre);
    Optional<Cementerio> findByTelefono(String telefono);
    List<Cementerio> findAllByAyuntamientoLocalidad(String localidad); // Como la localidad viene de otra tabla de la relación, hay que indicarle de qué tabla y qué campo
    List<Cementerio> findAllByAyuntamientoProvincia(String provincia);
    List<Cementerio> findAllByAyuntamientoId(Long id); // Estos metodos se colocan en el lado "debil" de las relciones, las que heredan la FK

}
