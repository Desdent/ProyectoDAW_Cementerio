package com.imo.cementery.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tarifa_servicio", uniqueConstraints = {
        // Esto impide que se repita la combinación de cementerio y servicio
        @UniqueConstraint(columnNames = {"cementerio_id", "servicio_id"})
})
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

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;


    // >> RELACIONES <<

    @ManyToOne
    @JoinColumn(name = "cementerio_id", nullable = false)
    private Cementerio cementerio;

    @ManyToOne
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;


}
