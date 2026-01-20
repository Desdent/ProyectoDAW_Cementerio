package com.imo.cementery.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "concesion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Concesion {

    // >> COLUMNAS <<

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double precio; // PREGUNTAR A MANOLO SI AQUÍ TAMBIEN VA PRECIO

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    @Column(nullable = false)
    @Builder.Default
    private Boolean vencida = false;


    // >> RELACIONES <<

    @OneToMany(mappedBy = "concesion", fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private List<Parcela> parcelas = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "pago_id")  // Concesion tiene la FK
    private Pago pago;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

}
