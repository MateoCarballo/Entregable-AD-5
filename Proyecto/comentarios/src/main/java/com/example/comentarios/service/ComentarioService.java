package com.example.comentarios.service;

import com.example.comentarios.model.Comentario;
import com.example.comentarios.model.CreateComentarioInput;
import com.example.comentarios.model.CreateComentarioPayload;
import com.example.comentarios.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ComentarioService {
    @Autowired
    private ComentarioRepository comentarioRepository;

    public CreateComentarioPayload crearComentario(CreateComentarioInput input) {

        // Realizar operaciones de validacion del usuario

        Comentario comentario = Comentario.builder()
                .id(UUID.randomUUID().toString())
                .usuarioId(1)
                .hotelId(1)
                .reservaId(input.getIdReserva())
                .puntuacion(1.11)
                .comentario(input.getComentario())
                .fechaCreacion(LocalDateTime.now())
                .build();
        Comentario comentarioGuardado = comentarioRepository.save(comentario);

        return new CreateComentarioPayload("Comentario creado con exito", comentarioGuardado);
    }

    public String eliminarComentarios() {
        comentarioRepository.deleteAll();
        return "Todos los comentarios han sido eliminados";
    }

    public List<Comentario> getComentarios() {
        return comentarioRepository.findAll();
    }

//    public Comentario getComentario(String id) {
//        return comentarioRepository.findById(id).orElse(null);
//    }

//
//    private boolean validarUsuario() {
//        return true;
//    }
//
//    private Integer obtenerIdUsuario() {
//
//        return 1;
//    }
//
//    private Integer obtenerIdHotel() {
//        return 1;
//    }
//
//    private boolean validarReserva() {
//        return true;
//    }
//
//    public String eliminarComentarioDeUsuario() {
//        return "Comentario eliminado correctamente";
//    }
//
//    public List<Comentario> listarComentariosHotel() {
//
//        return new ArrayList<>();
//    }
//
//    public List<Comentario> listarComentariosUsuario() {
//
//        return new ArrayList<>();
//    }
//
//    public Comentario mostrarComentarioUsuarioReserva() {
//        return new Comentario() ;
//    }
//
//    public Double puntuacionMediaHotel() {
//        return 0.0;
//    }
//
//    public Double puntuacionesMediasUsuario() {
//        return 0.0;
//    }
} 