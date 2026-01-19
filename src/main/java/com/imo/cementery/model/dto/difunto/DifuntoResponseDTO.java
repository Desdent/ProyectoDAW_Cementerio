package com.imo.cementery.model.dto.difunto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String mensaje;
    private String foto;

}
