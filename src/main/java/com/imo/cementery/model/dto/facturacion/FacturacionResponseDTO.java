package com.imo.cementery.model.dto.facturacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacturacionResponseDTO {

    private Long id;
    private String dni;
    private String nombre;
    private String apellido1;
    private String direccion;
    private String telefono;
    private BigDecimal importe;
    private Long pagoId;

}
