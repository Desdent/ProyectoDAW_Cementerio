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
    @NotBlank
    private String provincia;

}
