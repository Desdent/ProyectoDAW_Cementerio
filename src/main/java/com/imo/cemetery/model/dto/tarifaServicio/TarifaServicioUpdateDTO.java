package com.imo.cemetery.model.dto.tarifaServicio;

import jakarta.validation.constraints.Digits;
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
public class TarifaServicioUpdateDTO {

    @NotNull
    @Positive
    @Digits(integer = 8, fraction = 2)
    private BigDecimal precio;
    @NotNull
    private Long cementerioId;
    @NotNull
    private Long servicioId;

}
