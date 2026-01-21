package com.imo.cemetery.model.dto.difunto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Year;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DifuntoCreateDTO {

    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido1;
    private String apellido2;
    @NotNull
    @PastOrPresent
    private Year yearNacimiento;
    @NotNull
    @PastOrPresent
    private Year yearDefuncion;
    @NotNull
    private LocalDate fechaEntierro;
    @NotNull
    // #TODO añadir validador posterior a la fecha de defunción
    private String mensaje;
    private String foto;
    @NotNull
    private Long parcelaId;


}
