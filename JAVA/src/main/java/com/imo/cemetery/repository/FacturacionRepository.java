package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.Facturacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacturacionRepository extends JpaRepository<Facturacion, Long> {

    List<Facturacion> findAllByDni(String dni);

    List<Facturacion> findAllByNombre(String nombre);

    List<Facturacion> findAllByApellido1(String apellido1);

    List<Facturacion> findAllByNombreAndApellido1(String nombre, String apellido1);

    List<Facturacion> findAllByTelefono(String telefono);

    Optional<Facturacion> findByPagoId(Long id);

}
