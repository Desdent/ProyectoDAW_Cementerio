package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.Cementerio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CementerioRepository extends JpaRepository<Cementerio, Long> {

    Optional<Cementerio> findByNombre(String nombre);

    Optional<Cementerio> findByTelefono(String telefono);

    List<Cementerio> findAllByAyuntamientoCiudadProvinciaNombre(String nombreProvincia);

    List<Cementerio> findAllByAyuntamientoCiudadProvinciaId(Long provinciaId);

    List<Cementerio> findAllByAyuntamientoId(Long id); // Estos metodos se colocan en el lado "debil" de las relciones, las que heredan la FK
    

}
