package com.imo.cemetery.model.dto.parcela;

import com.imo.cemetery.model.enums.EstadoType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParcelaResponseDTO {

    private Long id;
    private Double coordenadaX;
    private Double coordenadaY;
    private Integer fila;
    private Integer columna;
    private Long concesionId;
    private Long zonaId;
    private EstadoType estado;

}
