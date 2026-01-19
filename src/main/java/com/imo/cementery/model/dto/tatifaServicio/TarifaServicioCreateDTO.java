package com.imo.cementery.model.dto.tatifaServicio;

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
public class TarifaServicioCreateDTO {

    @NotNull
    @Positive
    @Digits(integer = 5, fraction = 2)
    private Double precio;

}
