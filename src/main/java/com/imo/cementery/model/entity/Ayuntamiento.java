package com.imo.cementery.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="ayuntamiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ayuntamiento extends User {

    // No necesitan id al ser especializaciones, lo heredan de User

    // >> COLUMNAS <<
    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String telefono;

    @Column(nullable = false)
    private String direccion;

    @Column
    private String escudo;

    @Column(nullable = false)
    private String localidad;

    @Column(nullable = false)
    private String provincia;



    // >> RELACIONES <<
    @OneToMany(mappedBy = "ayuntamiento", fetch = FetchType.LAZY) //mappedBy se encarga de decirle a JPA que el dueño de la relacón indicada (quien hereda la FK) es esta entidad. Apunta al campo de la entidad asociada que se relaciona con esta
    @JsonIgnore
    private List<Cementerio> cementerios;



}
