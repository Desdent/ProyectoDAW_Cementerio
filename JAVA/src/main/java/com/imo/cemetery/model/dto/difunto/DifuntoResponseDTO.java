package com.imo.cemetery.model.dto.difunto;

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
public class DifuntoResponseDTO {

    private Long id;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private Year yearNacimiento;
    private Year yearDefuncion;
    private LocalDate fechaEntierro;
    private String mensaje;
    private String foto;
    private Long parcelaId;

}
