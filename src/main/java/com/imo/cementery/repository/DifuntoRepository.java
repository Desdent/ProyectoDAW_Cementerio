package com.imo.cementery.repository;

import com.imo.cementery.model.Cliente;
import com.imo.cementery.model.Difunto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@Repository
public interface DifuntoRepository extends JpaRepository<Difunto, Long> {

    List<Difunto> findAllByNombre(String nombre);
    List<Difunto> findAllByApellido1(String apellido1);
    List<Difunto> findAllByApellido2(String apellido2);
    List<Difunto> findByNombreAndApellido1(String nombre, String apellido1);
    List<Difunto> findByNombreAndApellido1AndApellido2(String nombre, String apellido1, String apellido2);
    List<Difunto> findAllByYearNacimiento(Year yearNacimiento);
    List<Difunto> findAllByYearDefuncion(Year yearDefuncion);
    List<Difunto> findAllByYearNacimientoAndYearDefuncion(Year yearNacimiento, Year yearDefuncion);
    List<Difunto> findAllByFechaEntierro(LocalDate fechaEntierro);
    List<Difunto> findAllByParcelaId(Long id);

}
