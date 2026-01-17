package com.imo.cementery.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Entity
@Table(name="difunto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private LocalDate fecha_entierro;

    @Column
    private String mensaje;

    @Column
    private String foto;


    // >> RELACIONES <<

    @ManyToOne
    @JoinColumn(name = "parcela_id", nullable = false)
    private Parcela parcela;



}
