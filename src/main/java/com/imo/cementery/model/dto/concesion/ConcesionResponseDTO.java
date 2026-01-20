package com.imo.cementery.model.dto.concesion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConcesionResponseDTO {

    private Long id;
    private Double precio;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean vencida;
    private Long clienteId;
    private Long pagoId;

}
