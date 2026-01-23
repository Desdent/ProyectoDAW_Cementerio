package com.imo.cemetery.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "ciudad")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ciudad {

    @Id
    private Long id;


    @Column(nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "provincia_id", nullable = false)
    private Provincia provincia;


    @OneToMany(mappedBy = "ciudad")
    private List<Cliente> clientes;

    @OneToOne(mappedBy = "ciudad")
    private Ayuntamiento ayuntamiento;

}
