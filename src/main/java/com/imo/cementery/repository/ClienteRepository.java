package com.imo.cementery.repository;

import com.imo.cementery.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email); // Al parecer spring es capaz de hacer el join hacia atrás por si mismo
    Optional<Cliente> findByDni(String dni);
    List<Cliente> findAllByNombre(String nombre);
    List<Cliente> findAllByApellido1(String apellido1);
    List<Cliente> findAllByApellido2(String apellido2);
    Optional<Cliente> findByNombreAndApellido1(String nombre, String apellido1);
    Optional<Cliente> findByNombreAndApellido1AndApellido2(String nombre, String apellido1, String apellido2);
    Optional<Cliente> findByTelefono(String telefono);
    List<Cliente> findAllByLocalidad(String localidad);
    List<Cliente> findAllByProvincia(String provincia);

}
