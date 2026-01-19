package com.imo.cementery.model.dto.difunto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DifuntoUpdateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El apellido1 es obligatorio")
    private String apellido1;
    private String apellido2;
    @NotNull(message = "El año de nacimiento es obligatorio")
    @PastOrPresent(message = "El año de nacimiento debe ser anterior o igual al actual")
    private Year yearNacimiento;
    @NotNull(message = "El año de defunción es obligatorio")
    @FutureOrPresent(message = "El año de defunción deebe ser posterior o igual al actual ")
    private Year yearDefuncion;
    @NotNull(message = "La fecha de entierro es obligatoria")
    // #TODO añadir validador posterior a la fecha de defunción
    private String mensaje;
    private String foto;

}
