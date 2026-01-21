package com.imo.cemetery.model.dto.cementerio;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CementerioUpdateDTO {

    @NotBlank
    private String nombre;
    @NotBlank
    private String direccion;
    @NotBlank
    private String telefono;


}
