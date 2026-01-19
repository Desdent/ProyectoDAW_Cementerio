package com.imo.cementery.model.dto.pago;

import com.imo.cementery.model.enums.PagoType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoCreateDTO {

    @NotNull
    @Positive
    private Double importe;
    @NotNull
    @PastOrPresent
    private LocalDate fecha;
    @NotNull
    private PagoType metodo;

}
