package com.imo.cementery.model;


import jakarta.persistence.*;
import lombok.*;

@Entity // Le dice al framework que esto es una entidad. De JPA
@Table(name = "users") // Nombre de la tabla. De JPA
@Getter // Crea los getters. De Lombok
@Setter // Crea los setters. De Lombok
@NoArgsConstructor // Crea constructor vacio. De Lombok
@AllArgsConstructor // Crea constructor con argumentos. De Lombok
public class User {

    @Id // Señala que este campo es el id de la tabla. De JPA
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Hace que cuando se cree la tabla en la bbdd se autogenere y autoincremente el ID. De JPA
    private Long id;


    @Column(nullable = false, unique = true) // Indica qe este campo es una columna en la bbdd, es unique y no puede ser nulo. De JPA
    private String email;


    @Column(nullable = false)
    private String password;

}
