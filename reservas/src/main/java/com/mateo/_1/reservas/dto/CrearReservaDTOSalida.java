package com.mateo._1.reservas.dto;

import com.mateo._1.reservas.enums.EstadoReserva;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CrearReservaDTOSalida {
    private int usuario_id;
    private int habitacion_id;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    @Builder.Default
    private EstadoReserva estadoReserva = EstadoReserva.Pendiente;

    public boolean comprobarFechas(){
        return(fecha_fin.isAfter(fecha_inicio));
    }
}

