package com.imo.cementery.model.dto.ayuntamiento;

import com.imo.cementery.model.dto.user.UserCreateDTO;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String localidad;
    @NotBlank
    private String provincia;


}
