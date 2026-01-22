package com.imo.cemetery.model.dto.pago;

import com.imo.cemetery.model.enums.PagoType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoCreateDTO {


    @NotNull
    @Positive
    @Digits(integer = 8, fraction = 2)
    private BigDecimal importe;
    @NotNull
    @PastOrPresent
    private LocalDate fecha;
    @NotNull
    private PagoType metodo;


}
