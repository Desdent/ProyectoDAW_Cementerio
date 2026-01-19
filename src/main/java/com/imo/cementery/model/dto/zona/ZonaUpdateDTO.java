package com.imo.cementery.model.dto.zona;

import com.imo.cementery.model.enums.ZonaType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZonaUpdateDTO {

    @NotNull
    private ZonaType tipo;

}
