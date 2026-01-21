package com.imo.cemetery.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ayuntamiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Ayuntamiento extends User {

    // No necesitan id al ser especializaciones, lo heredan de User

    // >> COLUMNAS <<
    @Column(nullable = false, unique = true)
    private String nif;

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
    @OneToMany(mappedBy = "ayuntamiento", fetch = FetchType.LAZY)
    //mappedBy se encarga de decirle a JPA que el dueño de la relacón indicada (quien hereda la FK) es esta entidad. Apunta al campo de la entidad asociada que se relaciona con esta
    @JsonIgnore
    @Builder.Default
    private List<Cementerio> cementerios = new ArrayList<>();


}
