package com.imo.cementery.model.dto.cliente;

import com.imo.cementery.model.dto.user.UserCreateDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder // Hace lo mismo que builder pero es necesario usar este cuando se vayan a usar padres e hijos para que herede sus campos de dto
@EqualsAndHashCode(callSuper = true) // Es aconsejable ponerlo cuando se use @SuperBuilder
public class ClienteCreateDTO extends UserCreateDTO {


    @NotBlank(message = "El dni es obligatorio")
    private String dni;
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido1;
    private String apellido2;
    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;
    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;
    @NotBlank(message = "La localidad es obligatoria")
    private String localidad;
    @NotBlank(message = "La provincia es obligatoria")
    private String provincia;

}
