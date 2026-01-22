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
public class ImplementacionServicioCreateDTO {

    @NotNull
    private LocalDate fechaRealizacion;
    @NotNull
    private Long parcelaId;
    @NotNull
    private Long servicioId;
    @NotNull
    private Long facturacionId;

}
