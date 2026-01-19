package com.imo.cementery.model.dto.servicio;

import com.imo.cementery.model.enums.ServicioType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioUpdateDTO {

    @NotNull
    private ServicioType tipo;

}
