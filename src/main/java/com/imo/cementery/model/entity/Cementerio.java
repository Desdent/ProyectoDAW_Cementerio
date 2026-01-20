package com.imo.cementery.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cementerio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cementerio {

    // >> CAMPOS <<

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false, unique = true)
    private String telefono;


    // >> RELACIONES <<

    @OneToMany(mappedBy = "cementerio", fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private List<Zona> zonas = new ArrayList<>();

    @OneToMany(mappedBy = "cementerio", fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private List<TarifaServicio> serviciosOfrecidos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "ayuntamiento_id", nullable = false)
    private Ayuntamiento ayuntamiento;
    // Esto hace que para la relacion 1:N con el objeto para el que se crea (Ayuntamiento) cree en esta entidad un campo una columna llamada "ayuntamiento_id"
    // que heredará la PK del objeto con el que se crea (Ayuntamiento) y la usará como la FK de la relación
}
