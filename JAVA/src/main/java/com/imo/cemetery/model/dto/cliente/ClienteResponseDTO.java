package com.imo.cemetery.model.dto.cliente;

import com.imo.cemetery.model.dto.user.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
// Hace lo mismo que builder pero es necesario usar este cuando se vayan a usar padres e hijos para que herede sus campos de dto
@EqualsAndHashCode(callSuper = true) // Es aconsejable ponerlo cuando se use @SuperBuilder
public class ClienteResponseDTO extends UserResponseDTO {

    private String dni;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String telefono;
    private String direccion;
    private String localidad;
    private String provincia;

}
