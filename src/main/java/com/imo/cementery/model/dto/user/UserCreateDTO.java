package com.imo.cementery.model.dto.user;

import com.imo.cementery.model.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data // Ya incluye el hashcode y el tostring
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder // Hace lo mismo que builder pero es necesario usar este cuando se vayan a usar padres e hijos
public class UserCreateDTO {

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato no válido")
    private String email;
    @NotBlank(message = "La password es obligatoria")
    @Size(min = 6, max = 20, message = "Mínimo 6 caracteres. Máximo 20 caracteres")
    private String password;
    @NotNull(message = "El role es obligatorio")
    private Role role;

}
