package com.imo.cemetery.model.dto.parcela;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParcelaUpdateDTO {

    @NotNull
    @Digits(integer = 3, fraction = 6)
    private Double coordenadaX;
    @NotNull
    @Digits(integer = 3, fraction = 6)
    private Double coordenadaY;
    @NotNull
    private Integer fila;
    @NotNull
    private Integer columna;
    @NotNull
    private Long ZonaId;
    @NotNull
    private Long concesionId;

}
