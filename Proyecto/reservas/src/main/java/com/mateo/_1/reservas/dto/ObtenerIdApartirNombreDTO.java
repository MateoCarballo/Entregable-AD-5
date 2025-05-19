package com.mateo._1.reservas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ObtenerIdApartirNombreDTO {
    private String nombreUsuario;
    private String contrasenaUsuario;
    private String nombre;
}
