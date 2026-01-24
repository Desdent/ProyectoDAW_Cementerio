package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email); // Al parecer spring es capaz de hacer el join hacia atrás por si mismo
    Optional<Cliente> findByDni(String dni);

    //Buscador
    List<Cliente> findAllByNombreContainingIgnoreCaseOrApellido1ContainingIgnoreCaseOrApellido2ContainingIgnoreCaseOrTelefonoContaining(String nombre, String ap1, String ap2, String telefono);

    // Localización
    List<Cliente> findAllByCiudadProvinciaNombreIgnoreCase(String nombreProvincia);
    List<Cliente> findAllByCiudadProvinciaId(Long provinciaId);
    List<Cliente> findAllByCiudadId(Long ciudadId);
    List<Cliente> findAllByCiudadNombreIgnoreCase(String ciudadNombre);

    boolean existsByEmail(String email);
    boolean existsByDni(String dni);



}
