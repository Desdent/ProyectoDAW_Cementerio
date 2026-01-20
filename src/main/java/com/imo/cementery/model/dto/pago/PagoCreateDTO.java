package com.imo.cementery.model.dto.pago;

import com.imo.cementery.model.enums.PagoType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoCreateDTO {


    @NotNull
    private PagoType metodo;

}
