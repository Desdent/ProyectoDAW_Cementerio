package com.imo.cementery.model.dto.role;

import com.imo.cementery.model.enums.RoleType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleCreateDTO {

    @NotNull
    private RoleType tipo;

}
