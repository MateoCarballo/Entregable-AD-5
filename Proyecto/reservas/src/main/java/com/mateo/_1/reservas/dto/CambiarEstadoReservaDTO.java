package com.mateo._1.reservas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CambiarEstadoReservaDTO {
    private String nombre;
    private String contrasena;
    private int reserva_id;
    private String estadoReserva;
}
