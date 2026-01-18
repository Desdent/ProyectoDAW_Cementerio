package com.imo.cementery.model.dto.ayuntamiento;

import com.imo.cementery.model.dto.user.UserCreateDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AyuntamientoCreateDTO extends UserCreateDTO {

    @NotBlank(message = "El nif es obligatorio")
    private String nif;
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;
    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;
    private String escudo;
    @NotBlank(message = "La localidad es obligatoria")
    private String localidad;
    @NotBlank(message = "La provincia es obligatoria")
    private String provincia;


}
