package com.imo.cemetery.model.dto.zona;

import com.imo.cemetery.model.enums.ZonaType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZonaCreateDTO {

    @NotNull
    private ZonaType tipo;
    @NotNull
    private String nombre;
    @NotNull
    private Long cementerioId;

}
