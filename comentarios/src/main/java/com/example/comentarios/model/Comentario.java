package com.example.comentarios.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "comentarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comentario {
    @Id // Asegúrate de que el ID esté marcado para MongoDB
    private String id;
    private Integer usuarioId;
    private Integer hotelId;
    private Integer reservaId;
    private Double puntuacion;
    private String comentario;
    private LocalDateTime fechaCreacion;
}