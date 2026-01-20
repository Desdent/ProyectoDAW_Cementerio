package com.imo.cementery.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parcela")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Parcela {

    // >> COLUMNAS <<

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double coordenadaX;

    @Column(nullable = false)
    private Double coordenadaY;

    @Column
    private Integer fila; // Opcional porque solo se rellena para cuando esté en una fila de nichos. Es Integer porque int no acepta nulos

    @Column
    private Integer columna; // Opcional porque solo se rellena para cuadno esté en una fila de nichos


    // >> RELACIONES <<

    @OneToMany(mappedBy = "parcela", fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private List<ImplementacionServicio> implementacionesEnParcela = new ArrayList<>();

    @OneToMany(mappedBy = "parcela", fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private List<Difunto> difuntos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "concesion_id")
    private Concesion concesion;

    @ManyToOne
    @JoinColumn(name = "zona_id")
    private Zona zona;


}
