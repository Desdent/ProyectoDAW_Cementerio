package com.imo.cemetery.model.dto.pago;

import com.imo.cemetery.model.enums.PagoType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoUpdateDTO {


    @NotNull
    private PagoType metodo;


}
