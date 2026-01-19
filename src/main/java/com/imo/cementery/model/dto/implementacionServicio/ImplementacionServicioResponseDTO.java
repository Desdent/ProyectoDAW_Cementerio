package com.imo.cementery.model.dto.implementacionServicio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImplementacionServicioResponseDTO {

    private Long id;
    private LocalDate fechaRealizacion;

}
