package com.imo.cementery.model.dto.concesion;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConcesionCreateDTO {

    @NotNull
    @Positive
    private Double precio;
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
