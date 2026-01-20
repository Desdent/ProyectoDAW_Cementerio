package com.imo.cementery.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imo.cementery.model.enums.PagoType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "pago")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {


    // >> CAMPOS

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double importe;

    @Column(nullable = false)
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    // Esto le indica a JPA que la columna es un Enum y el EnumType.String hace que guarde los valores en lugar de las posiciones. No se usa anotacion de relación
    @Column(nullable = false)
    private PagoType metodo;


    // >> RELACIONES <<

    @OneToOne(mappedBy = "pago", fetch = FetchType.LAZY)
    @JsonIgnore
    private Facturacion facturacion;


    @OneToOne(mappedBy = "pago", fetch = FetchType.LAZY)
    @JsonIgnore
    private Concesion concesion;


}
