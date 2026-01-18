package com.imo.cementery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="concesion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Concesion {

    // >> COLUMNAS <<

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double precio; // PREGUNTAR A MANOLO SI AQUÍ TAMBIEN VA PRECIO

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    @Column(nullable = false)
    private boolean vencida;


    // >> RELACIONES <<

    @OneToMany(mappedBy = "concesion", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Parcela> parcelas;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "pago_id")  // Concesion tiene la FK
    private Pago pago;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

}
