package com.mateo._1.reservas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActualizarHotelDTO {
    private int id;
    private String nombre;
    private String nombreUsuario;
    private String contrasenaUsuario;
    private String direccion;
}
