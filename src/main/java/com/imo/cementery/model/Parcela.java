package com.imo.cementery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="parcela")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Parcela {

    // >> COLUMNAS <<

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double coordenadaX;

    @Column(nullable = false)
    private double coordenadaY;

    @Column
    private Integer fila; // Opcional porque solo se rellena para cuando esté en una fila de nichos. Es Integer porque int no acepta nulos

    @Column
    private Integer columna; // Opcional porque solo se rellena para cuadno esté en una fila de nichos


    // >> RELACIONES <<

    @OneToMany(mappedBy = "parcela", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ImplementacionServicio> implementacionesEnParcela;

    @OneToMany(mappedBy = "parcela", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Difunto> difuntos;

    @ManyToOne
    @JoinColumn(name = "concesion_id")
    private Concesion concesion;

    @ManyToOne
    @JoinColumn(name = "zona_id")
    private Zona zona;


}
