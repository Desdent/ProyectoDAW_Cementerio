package com.imo.cementery.model.dto.pago;

import com.imo.cementery.model.enums.PagoType;
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
public class PagoResponseDTO {

    private Long id;
    private BigDecimal importe;
    private LocalDate fecha;
    private PagoType metodo;

}
