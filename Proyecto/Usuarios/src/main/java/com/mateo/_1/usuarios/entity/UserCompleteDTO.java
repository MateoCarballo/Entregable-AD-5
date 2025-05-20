package com.mateo._1.usuarios.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCompleteDTO {
    private int u_id;
    private String u_nombre;
    private String u_correo_electronico;
    private String u_direccion;
    private String u_contrasena;
}
