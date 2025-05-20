package com.mateo._1.usuarios.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCompleteDTO {
    private int id;
    private String nombre;
    private String correo_electronico;
    private String direccion;
    private String contrasena;
}
