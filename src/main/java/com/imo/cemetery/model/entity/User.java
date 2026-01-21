package com.imo.cemetery.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity // Le dice al framework que esto es una entidad. De JPA
@Table(name = "users") // Nombre de la tabla. De JPA
@Inheritance(strategy = InheritanceType.JOINED)
// Este tipo de gerenciae encarga de relacionar como herencia con las tablas que extiendan de ésta,
// insertando los datos de las hijas en el padre cuando éstas se crean.
// Además, cuando se crea la tabla padre (users) y se genera la id, hibernate se encarga de pasárselo a la tabla hija que ha creado los datos previos.
// No es necesario crear la relación con @oneToMany ni similar
@Getter // Crea los getters. De Lombok
@Setter // Crea los setters. De Lombok
@NoArgsConstructor // Crea constructor vacio. De Lombok
@AllArgsConstructor // Crea constructor con argumentos. De Lombok
@SuperBuilder // Permite usar .builder y .build en el mapper
public class User implements UserDetails {

    // >> COLUMNAS <<

    @Id // Señala que este campo es el id de la tabla. De JPA
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Hace que cuando se cree la tabla en la bbdd se autogenere y autoincremente el ID. De JPA
    private Long id;


    @Column(nullable = false, unique = true)
    // Indica qe este campo es una columna en la bbdd, es unique y no puede ser nulo. De JPA
    private String email;


    @Column(nullable = false)
    private String password;


    // >> RELACIONES <<

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.getTipo().name()));
    }
    // Este metodo espera que se le devuelva una lista, que sería el caso si pudieran haber usuarios multi-rol
    // Como no es el caso en la aplicación, se le dice que en lugar de obtener un stream, mapearlo a SimpleGranthedAuthority (esto hay que hacerlo SI o SI) y guardarlo en una lista
    // que lo que haga en su lugar sea hacer una lista del SimpleGranthedAuthority del rol del usuario

    @Override
    public String getUsername() {
        return this.getEmail();
    }
}
