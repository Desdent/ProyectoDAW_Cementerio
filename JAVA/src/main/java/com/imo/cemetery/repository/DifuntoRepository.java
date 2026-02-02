package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.Difunto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Repository
public interface DifuntoRepository extends JpaRepository<Difunto, Long> {

    List<Difunto> findAllByNombreContainingIgnoreCase(String nombre);
    List<Difunto> findAllByApellido1ContainingIgnoreCase(String apellido1);
    List<Difunto> findAllByApellido2ContainingIgnoreCase(String apellido2);
    List<Difunto> findByNombreAndApellido1(String nombre, String apellido1);
    List<Difunto> findByNombreContainingIgnoreCaseOrApellido1ContainingIgnoreCaseOrApellido2ContainingIgnoreCase(String nombre, String apellido1, String apellido2);
    List<Difunto> findAllByYearNacimiento(Year yearNacimiento);
    List<Difunto> findAllByYearDefuncion(Year yearDefuncion);
    List<Difunto> findAllByYearNacimientoAndYearDefuncion(Year yearNacimiento, Year yearDefuncion);
    List<Difunto> findAllByFechaEntierro(LocalDate fechaEntierro);
    List<Difunto> findAllByParcelaId(Long id);

    List<Difunto> findAllByParcelaZonaId(Long zonaId);
    List<Difunto> findAllByParcelaZonaCementerioAyuntamientoId(Long id);
    List<Difunto> findAllByParcelaZonaCementerioId(Long id);
    List<Difunto> findAllByParcelaConcesionClienteId(Long id);

    @Query("SELECT DISTINCT d FROM Difunto d " +
            "JOIN d.parcela p " +
            "JOIN p.concesion con " +
            "WHERE con.cliente.id = :clienteId " +
            "AND p.zona.cementerio.ayuntamiento.id = :aytoId")
    List<Difunto> findDifuntosByClienteAndAyuntamiento(
            @Param("clienteId") Long clienteId,
            @Param("aytoId") Long aytoId
    );
}
