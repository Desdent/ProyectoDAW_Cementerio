package com.imo.cemetery.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Year;

@Entity
@Table(name = "difunto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Difunto {

    // >> COLUMNAS <<

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido1;

    @Column
    private String apellido2;

    @Column(nullable = false)
    private Year yearNacimiento;

    @Column(nullable = false)
    private Year yearDefuncion;

    @Column(nullable = false)
    private LocalDate fechaEntierro;

    @Column
    private String mensaje;

    @Column
    private String foto;


    // >> RELACIONES <<

    @ManyToOne
    @JoinColumn(name = "parcela_id", nullable = false)
    private Parcela parcela;


}
