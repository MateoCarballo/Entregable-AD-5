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
public class CrearReservaDTOEntrada {
    //(fecha_inicio, fecha_fin y habitacion_id)
    private int habitacion_id;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private String usuario;
    private String contrasena;

    public boolean comprobarFechas(){
        return(fecha_fin.isAfter(fecha_inicio));
    }
}
