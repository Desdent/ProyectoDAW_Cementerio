package com.imo.cemetery.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imo.cemetery.model.enums.EstadoType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parcela", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"fila", "columna", "zona_id"}),
        @UniqueConstraint(columnNames = {"coordenadaX", "coordenadaY"})
})
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

    @Column(nullable = false)
    private Integer fila;

    @Column(nullable = false)
    private Integer columna;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoType estado;


    // >> RELACIONES <<

    @OneToMany(mappedBy = "parcela", fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private List<ImplementacionServicio> implementacionesEnParcela = new ArrayList<>();

    @OneToMany(mappedBy = "parcela", fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private List<Difunto> difuntos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concesion_id")
    private Concesion concesion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zona_id")
    private Zona zona;

    public boolean isLibre() {

        return this.estado == EstadoType.LIBRE && this.concesion == null;
    }


}
