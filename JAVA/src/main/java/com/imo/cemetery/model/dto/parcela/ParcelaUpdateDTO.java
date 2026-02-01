package com.imo.cemetery.model.dto.parcela;

import com.imo.cemetery.model.enums.EstadoType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @Positive
    private Integer fila;
    @NotNull
    @Positive
    private Integer columna;
    @NotNull
    private Long ZonaId;
    @NotNull
    private Long concesionId;
    @NotNull
    private EstadoType estado;

}
