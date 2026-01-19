package com.imo.cementery.model.dto.tatifaServicio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TarifaServicioResponseDTO {

    private Long id;
    private Double precio;

}
