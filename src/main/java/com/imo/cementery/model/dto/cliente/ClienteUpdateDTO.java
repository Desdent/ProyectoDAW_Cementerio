package com.imo.cementery.model.dto.cliente;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteUpdateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido1;
    private String apellido2;
    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;
    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;
    @NotBlank(message = "La localidad es obligatoria")
    private String localidad;
    @NotBlank(message = "La provincia es obligatoria")
    private String provincia;

}
