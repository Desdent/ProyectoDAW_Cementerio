package com.imo.cemetery.model.dto.implementacionServicio;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImplementacionServicioUpdateDTO {

    @NotNull
    private LocalDate fechaRealizacion;
    private Long parcelaId;
    private Long servicioId;
    private Long facturacionId;

}
