package com.imo.cemetery.model.dto.provincia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProvinciaResponseDTO {
    private Long id;
    private String nombre;
}
