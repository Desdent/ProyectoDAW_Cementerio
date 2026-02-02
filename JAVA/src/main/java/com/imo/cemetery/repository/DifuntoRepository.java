package com.imo.cemetery.repository;

import com.imo.cemetery.model.entity.Difunto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

/**
 * The interface Difunto repository.
 */
@Repository
public interface DifuntoRepository extends JpaRepository<Difunto, Long> {

    /**
     * Find all by nombre containing ignore case list.
     *
     * @param nombre the nombre
     * @return the list
     */
    List<Difunto> findAllByNombreContainingIgnoreCase(String nombre);

    /**
     * Find all by apellido 1 containing ignore case list.
     *
     * @param apellido1 the apellido 1
     * @return the list
     */
    List<Difunto> findAllByApellido1ContainingIgnoreCase(String apellido1);

    /**
     * Find all by apellido 2 containing ignore case list.
     *
     * @param apellido2 the apellido 2
     * @return the list
     */
    List<Difunto> findAllByApellido2ContainingIgnoreCase(String apellido2);

    /**
     * Find by nombre and apellido 1 list.
     *
     * @param nombre    the nombre
     * @param apellido1 the apellido 1
     * @return the list
     */
    List<Difunto> findByNombreAndApellido1(String nombre, String apellido1);

    /**
     * Find by nombre containing ignore case or apellido 1 containing ignore case or apellido 2 containing ignore case list.
     *
     * @param nombre    the nombre
     * @param apellido1 the apellido 1
     * @param apellido2 the apellido 2
     * @return the list
     */
    List<Difunto> findByNombreContainingIgnoreCaseOrApellido1ContainingIgnoreCaseOrApellido2ContainingIgnoreCase(String nombre, String apellido1, String apellido2);

    /**
     * Find all by year nacimiento list.
     *
     * @param yearNacimiento the year nacimiento
     * @return the list
     */
    List<Difunto> findAllByYearNacimiento(Year yearNacimiento);

    /**
     * Find all by year defuncion list.
     *
     * @param yearDefuncion the year defuncion
     * @return the list
     */
    List<Difunto> findAllByYearDefuncion(Year yearDefuncion);

    /**
     * Find all by year nacimiento and year defuncion list.
     *
     * @param yearNacimiento the year nacimiento
     * @param yearDefuncion  the year defuncion
     * @return the list
     */
    List<Difunto> findAllByYearNacimientoAndYearDefuncion(Year yearNacimiento, Year yearDefuncion);

    /**
     * Find all by fecha entierro list.
     *
     * @param fechaEntierro the fecha entierro
     * @return the list
     */
    List<Difunto> findAllByFechaEntierro(LocalDate fechaEntierro);

    /**
     * Find all by parcela id list.
     *
     * @param id the id
     * @return the list
     */
    List<Difunto> findAllByParcelaId(Long id);

    /**
     * Find all by parcela zona id list.
     *
     * @param zonaId the zona id
     * @return the list
     */
    List<Difunto> findAllByParcelaZonaId(Long zonaId);

    /**
     * Find all by parcela zona cementerio ayuntamiento id list.
     *
     * @param id the id
     * @return the list
     */
    List<Difunto> findAllByParcelaZonaCementerioAyuntamientoId(Long id);

    /**
     * Find all by parcela zona cementerio id list.
     *
     * @param id the id
     * @return the list
     */
    List<Difunto> findAllByParcelaZonaCementerioId(Long id);

    /**
     * Find all by parcela concesion cliente id list.
     *
     * @param id the id
     * @return the list
     */
    List<Difunto> findAllByParcelaConcesionClienteId(Long id);

    /**
     * Find difuntos by cliente and ayuntamiento list.
     *
     * @param clienteId the cliente id
     * @param aytoId    the ayto id
     * @return the list
     */
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
