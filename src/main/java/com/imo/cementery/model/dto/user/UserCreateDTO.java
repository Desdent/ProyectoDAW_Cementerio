package com.imo.cementery.model.dto.user;

import com.imo.cementery.model.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data // Ya incluye el hashcode y el tostring
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder // Hace lo mismo que builder pero es necesario usar este cuando se vayan a usar padres e hijos
public class UserCreateDTO {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 6, max = 20, message = "Mínimo 6 caracteres. Máximo 20 caracteres")
    private String password;
    @NotNull
    private Role role;

}
