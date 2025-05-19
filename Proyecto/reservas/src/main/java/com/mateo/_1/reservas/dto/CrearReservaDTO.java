package com.mateo._1.reservas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CrearReservaDTO {
    private int id;
    private int usuarioId;
    private int habitacionId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;

    public boolean comprobarFechas(){
        return(fechaFin.isAfter(fechaInicio));
    }
}
