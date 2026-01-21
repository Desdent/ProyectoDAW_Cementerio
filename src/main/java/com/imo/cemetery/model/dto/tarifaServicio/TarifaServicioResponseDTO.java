package com.imo.cemetery.model.dto.tarifaServicio;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TarifaServicioResponseDTO {

    private Long id;
    private BigDecimal precio;
    @NotNull
    private Long cementerioId;
    @NotNull
    private Long servicioId;

}
