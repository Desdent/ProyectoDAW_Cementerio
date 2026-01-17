package com.imo.cementery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="zona")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Zona {

    // >> COLUMNAS <<

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) // Esto le indica a JPA que la columna es un Enum y el EnumType.String hace que guarde los valores en lugar de las posiciones. No se usa anotacion de relación
    @Column(nullable = false)
    private ZonaType tipo;


    // >> RELACIONES <<

    @OneToMany(mappedBy = "zona", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Parcela> parcelas;

    @ManyToOne
    @JoinColumn(name = "cementerio_id", nullable = false)
    private Cementerio cementerio;



}
