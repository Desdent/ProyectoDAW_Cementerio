package com.imo.cemetery.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "provincia")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Provincia {

    @Id
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "provincia", fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private List<Ciudad> ciudades = new ArrayList<>();



}
