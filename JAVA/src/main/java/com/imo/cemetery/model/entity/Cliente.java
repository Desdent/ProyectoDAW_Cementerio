package com.imo.cemetery.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Cliente extends User {

    // No necesitan id al ser especializaciones, lo heredan de User

    // >> COLUMNAS <<

    @Column(nullable = false, unique = true)
    private String dni;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido1;

    @Column
    private String apellido2;

    @Column(nullable = false, unique = true)
    private String telefono;

    @Column(nullable = false)
    private String direccion;


    // >> RELACIONES <<

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private List<Concesion> concesiones = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "ciudad_id", nullable = false)
    private Ciudad ciudad;

}
