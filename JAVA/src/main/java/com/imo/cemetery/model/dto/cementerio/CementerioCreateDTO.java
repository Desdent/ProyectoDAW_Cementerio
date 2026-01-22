package com.imo.cemetery.model.dto.cementerio;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CementerioCreateDTO {

    @NotBlank
    private String nombre;
    @NotBlank
    private String direccion;
    @NotBlank
    private String telefono;
    @NotNull(message = "El cementerio debe estar vinculado a un ayuntamiento")
    private Long ayuntamientoId;

}

