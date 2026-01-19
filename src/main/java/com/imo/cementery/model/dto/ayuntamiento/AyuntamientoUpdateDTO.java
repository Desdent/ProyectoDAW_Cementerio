package com.imo.cementery.model.dto.ayuntamiento;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AyuntamientoUpdateDTO {


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
