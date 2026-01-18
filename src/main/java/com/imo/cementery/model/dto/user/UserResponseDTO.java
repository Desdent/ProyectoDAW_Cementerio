package com.imo.cementery.model.dto.user;

import com.imo.cementery.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data // Ya incluye el hashcode y el tostring
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder // Hace lo mismo que builder pero es necesario usar este cuando se vayan a usar padres e hijos
public class UserResponseDTO {

    private Long id;
    private String email;
    private Role role;

}
