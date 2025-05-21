package com.example.comentarios.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "comentarios")
public class Comentario {
    @Id
    private String id;
    private Integer usuarioId;
    private Integer hotelId;
    private Integer reservaId;
    private Double puntuacion;
    private String comentario;
    private LocalDateTime fechaCreacion;
} 