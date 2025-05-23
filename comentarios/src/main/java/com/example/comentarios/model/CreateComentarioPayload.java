package com.example.comentarios.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateComentarioPayload { // Asegúrate de que el nombre de la clase sea correcto (Payload con 'a')
    private String message; // Corregido 'menssage'
    private Comentario comentario; // Corregido de List<Comentario> a Comentario
}
