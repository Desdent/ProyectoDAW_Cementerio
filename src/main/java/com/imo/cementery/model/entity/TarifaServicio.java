package com.imo.cementery.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "compraServicio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TarifaServicio {

    // >> COLUMNAS <<

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double precio;


    // >> RELACIONES <<

    @ManyToOne
    @JoinColumn(name = "cementerio_id", nullable = false)
    private Cementerio cementerio;

    @ManyToOne
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;


}
