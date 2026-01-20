package com.imo.cementery.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imo.cementery.model.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    // Esto le indica a JPA que la columna es un Enum y el EnumType.String hace que guarde los valores en lugar de las posiciones. No se usa anotacion de relación
    @Column(nullable = false, unique = true)
    private RoleType tipo;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private List<User> users = new ArrayList<>();


}
