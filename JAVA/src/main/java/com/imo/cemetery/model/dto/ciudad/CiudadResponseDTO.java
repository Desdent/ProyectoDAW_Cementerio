package com.imo.cemetery.model.dto.ciudad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CiudadResponseDTO {
    private Long id;
    private String nombre;
    private String nombreProvincia; // Para que Angular no tenga que buscarla
}
