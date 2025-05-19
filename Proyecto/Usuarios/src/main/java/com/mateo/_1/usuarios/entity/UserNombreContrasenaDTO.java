package com.mateo._1.usuarios.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserNombreContrasenaDTO {
    private String nombreUsuario;
    private String contrasenaUsuario;
}
