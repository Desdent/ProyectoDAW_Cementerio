package com.imo.cemetery.model.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;


}
