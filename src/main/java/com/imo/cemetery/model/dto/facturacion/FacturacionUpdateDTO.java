package com.imo.cemetery.model.dto.facturacion;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacturacionUpdateDTO {

    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido1;
    @NotBlank
    private String direccion;
    @NotBlank
    // #TODO: Añadir validator de telefono
    private String telefono;
    @NotNull
    @Positive
    @Digits(integer = 8, fraction = 2)
    private BigDecimal importe;

}
