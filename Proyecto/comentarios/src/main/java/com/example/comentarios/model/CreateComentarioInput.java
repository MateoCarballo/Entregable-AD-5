package com.example.comentarios.model;

import lombok.Data;

@Data
public class CreateComentarioInput {
    private String nombreHotel;
    private Integer idReserva; // Int en GraphQL -> Integer en Java
    private Double puntuacion; // Float en GraphQL -> Double en Java
    private String comentario;
    private String usuario; // Corresponde al campo 'usuario' en el input
    private String contrasena; // Corresponde al campo 'contrasena' en el input
}
