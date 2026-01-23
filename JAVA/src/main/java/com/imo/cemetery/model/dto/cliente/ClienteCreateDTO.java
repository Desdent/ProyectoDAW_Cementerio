package com.imo.cemetery.model.dto.cliente;

import com.imo.cemetery.model.dto.user.UserCreateDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
// Hace lo mismo que builder, pero es necesario usar este cuando se vayan a usar padres e hijos para que herede sus campos de dto.
@EqualsAndHashCode(callSuper = true) // Es aconsejable ponerlo cuando se use @SuperBuilder
public class ClienteCreateDTO extends UserCreateDTO {


    @NotBlank
    private String dni;
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido1;
    private String apellido2;
    @NotBlank
    private String telefono;
    @NotBlank
    private String direccion;
    @NotBlank
    private String localidad;
    @NotNull
    @Positive
    private Long ciudadId;

}
