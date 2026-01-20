package com.imo.cementery.model.dto.zona;

import com.imo.cementery.model.enums.ZonaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZonaResponseDTO {

    private Long id;
    private ZonaType tipo;
    private String nombre;
    private Long cementerioId;

}
