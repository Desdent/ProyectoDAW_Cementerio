package com.imo.cementery.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imo.cementery.model.enums.ServicioType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "servicio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Servicio {


    // >> CAMPOS <<

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    // Esto le indica a JPA que la columna es un Enum y el EnumType.String hace que guarde los valores en lugar de las posiciones. No se usa anotacion de relación
    @Column(nullable = false)
    private ServicioType tipo;


    // >> RELACIONES <<

    @OneToMany(mappedBy = "servicio", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TarifaServicio> disponibilidadEnCementerios;

    @OneToMany(mappedBy = "servicio", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ImplementacionServicio> implementacionesServicios;

}
