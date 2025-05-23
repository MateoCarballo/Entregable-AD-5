package com.mateo._1.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserNombreContrasenaDTO {
    private String nombre;
    private String contrasena;
}
// Aqui le pongo esto para hacerlo coincidir con el dato que espera en la otra parte cuando hago la llamada 
// desde el microservicio de reservas por eso los nombres no son exactos. Espero acabar a tiempo para arreglar estas cosillas.
