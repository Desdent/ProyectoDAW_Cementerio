package com.imo.cemetery.model.dto.concesion;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConcesionCreateDTO {

    @NotNull
    @Positive
    @Digits(integer = 8, fraction = 2)
    private BigDecimal precio;
    @NotNull
    @FutureOrPresent
    private LocalDate fechaInicio;
    @NotNull
    @FutureOrPresent
    // #TODO Cambiar el validator por un mas tarde que la compra
    private LocalDate fechaFin;
    @NotNull
    private Long clienteId;
    @NotNull
    private Long pagoId;
}
