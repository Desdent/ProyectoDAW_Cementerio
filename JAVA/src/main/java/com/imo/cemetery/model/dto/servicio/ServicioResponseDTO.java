package com.imo.cemetery.model.dto.servicio;

import com.imo.cemetery.model.enums.ServicioType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioResponseDTO {

    private Long id;
    private ServicioType tipo;

}
