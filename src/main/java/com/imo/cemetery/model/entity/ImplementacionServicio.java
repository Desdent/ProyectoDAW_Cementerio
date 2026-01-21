package com.imo.cemetery.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "implementacionServicio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImplementacionServicio {


    // >> CAMPOS <<

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fechaRealizacion;


    // >> RELACIONES <<

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parcela_id", nullable = false)
    private Parcela parcela;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facturacion_id", nullable = false)
    private Facturacion facturacion;

}
