package com.imo.cemetery.model.dto.ayuntamiento;

import com.imo.cemetery.model.dto.user.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AyuntamientoResponseDTO extends UserResponseDTO {

    private String nif;
    private String nombre;
    private String telefono;
    private String direccion;
    private String escudo;
    private String nombreCiudad;
    private String nombreProvincia;

}
