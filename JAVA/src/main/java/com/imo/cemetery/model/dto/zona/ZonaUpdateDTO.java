package com.imo.cemetery.model.dto.zona;

import com.imo.cemetery.model.enums.ZonaType;
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
public class ZonaUpdateDTO {

    @NotNull
    private ZonaType tipo;
    @NotNull
    private String nombre;
    private String puntos;
    @NotNull
    @Positive
    private Integer filas;
    @NotNull
    @Positive
    private Integer columnas;
    @NotNull
    private Long cementerioId;

}
