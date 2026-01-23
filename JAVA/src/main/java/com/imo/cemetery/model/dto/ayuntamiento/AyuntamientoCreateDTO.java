package com.imo.cemetery.model.dto.ayuntamiento;

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
@EqualsAndHashCode(callSuper = true)
public class AyuntamientoCreateDTO extends UserCreateDTO {

    @NotBlank
    private String nif;
    @NotBlank
    private String nombre;
    @NotBlank
    private String telefono;
    @NotBlank
    private String direccion;
    private String escudo;
    @NotNull
    @Positive
    private Long ciudadId;

}
