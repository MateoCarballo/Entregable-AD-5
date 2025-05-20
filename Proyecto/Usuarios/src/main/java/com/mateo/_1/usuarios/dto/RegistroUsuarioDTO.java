package com.mateo._1.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistroUsuarioDTO {
    private String nombre;
    private String correo_electronico;
    private String direccion;
    private String contrasena;
}
