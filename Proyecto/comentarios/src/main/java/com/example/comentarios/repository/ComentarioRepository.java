package com.example.comentarios.repository;

import com.example.comentarios.model.Comentario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends MongoRepository<Comentario, String> {
    Comentario findByUsuarioIdAndHotelIdAndReservaId(Integer usuarioId, Integer hotelId, Integer reservaId);
}