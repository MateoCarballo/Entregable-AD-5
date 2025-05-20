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
public class ListarReservasDTO {
    private int habitacionId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== Reserva =====\n");
        sb.append("Habitación ID : ").append(habitacionId).append("\n");
        sb.append("Fecha Inicio  : ").append(fechaInicio).append("\n");
        sb.append("Fecha Fin     : ").append(fechaFin).append("\n");
        sb.append("===================\n");
        return sb.toString();
    }

}
