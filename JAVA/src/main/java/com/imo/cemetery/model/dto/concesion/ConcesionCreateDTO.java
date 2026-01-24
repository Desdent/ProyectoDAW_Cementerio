package com.imo.cemetery.model.dto.concesion;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
    @NotNull
    @Size(min = 1, message = "Una concesión debe tener al menos una parcela")
    private List<Long> parcelaIds;
}
