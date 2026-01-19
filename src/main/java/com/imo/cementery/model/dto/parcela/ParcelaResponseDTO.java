package com.imo.cementery.model.dto.parcela;

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

}
