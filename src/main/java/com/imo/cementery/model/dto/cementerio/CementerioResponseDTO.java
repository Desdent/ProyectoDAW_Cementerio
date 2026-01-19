package com.imo.cementery.model.dto.cementerio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CementerioResponseDTO {

    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private Long ayuntamientoId;

}
