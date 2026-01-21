package com.imo.cemetery.model.dto.role;

import com.imo.cemetery.model.enums.RoleType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleUpdateDTO {

    @NotNull
    private RoleType tipo;

}
