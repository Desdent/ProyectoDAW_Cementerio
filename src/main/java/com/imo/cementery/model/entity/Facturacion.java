package com.imo.cementery.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "facturacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Facturacion {


    // >> CAMPOS <<

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // No es unique para permitir varias facturaciones a un mismo dni
    private String dni;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido1;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false) // El mismo caso que con dni
    private String telefono;

    @Column(nullable = false)
    private Double importe;


    // >> RELACIONES <<

    @OneToMany(mappedBy = "facturacion", fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private List<ImplementacionServicio> implementacionesServicios = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pago_id")  // Facturacion tiene la FK
    private Pago pago;

}
