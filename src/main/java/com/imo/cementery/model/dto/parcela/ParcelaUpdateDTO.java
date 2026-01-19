package com.imo.cementery.model.dto.parcela;

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
    //Confirmar si fila y columna son solo para nichos y cripta o para todos
    private Integer fila;
    private Integer columna;

}
