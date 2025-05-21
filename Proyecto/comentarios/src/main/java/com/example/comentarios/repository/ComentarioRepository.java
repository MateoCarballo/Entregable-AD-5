package com.example.comentarios.repository;

import com.example.comentarios.model.Comentario;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ComentarioRepository extends MongoRepository<Comentario, String> {
    List<Comentario> findByHotelId(Integer hotelId);
    List<Comentario> findByUsuarioId(Integer usuarioId);
    Comentario findByUsuarioIdAndReservaId(Integer usuarioId, Integer reservaId);
    boolean existsByUsuarioIdAndHotelIdAndReservaId(Integer usuarioId, Integer hotelId, Integer reservaId);
} 