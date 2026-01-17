package com.imo.cementery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="facturacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Facturacion {


    // >> CAMPOS <<

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido1;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private double importe;


    // >> RELACIONES <<

    @OneToMany(mappedBy = "facturacion", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ImplementacionServicio> implementacionesServicios;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pago_id")  // Facturacion tiene la FK
    private Pago pago;

}
