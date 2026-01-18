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


    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;
    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;
    private String escudo;
    @NotBlank(message = "La localidad es obligatoria")
    private String localidad;
    @NotBlank(message = "La provincia es obligatoria")
    private String provincia;

}
