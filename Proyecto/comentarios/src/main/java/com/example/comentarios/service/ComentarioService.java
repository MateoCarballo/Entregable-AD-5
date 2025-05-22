package com.example.comentarios.service;

import com.example.comentarios.model.Comentario;
import com.example.comentarios.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ComentarioService {
    @Autowired
    private ComentarioRepository comentarioRepository;

    private boolean validarUsuario() {
        return true;
    }

    private Integer obtenerIdUsuario() {

        return 1;
    }

    private Integer obtenerIdHotel() {
        return 1;
    }

    private boolean validarReserva() {
        return true;
    }

    public Comentario crearComentario() {
        return new Comentario();
    }

    public String eliminarComentarios() {
        comentarioRepository.deleteAll();
        return "Todos los comentarios han sido eliminados";
    }

    public String eliminarComentarioDeUsuario() {
        return "Comentario eliminado correctamente";
    }

    public List<Comentario> listarComentariosHotel() {

        return new ArrayList<>();
    }

    public List<Comentario> listarComentariosUsuario() {

        return new ArrayList<>();
    }

    public Comentario mostrarComentarioUsuarioReserva() {
        return new Comentario() ;
    }

    public Double puntuacionMediaHotel() {
        return 0.0;
    }

    public Double puntuacionesMediasUsuario() {
        return 0.0;
    }
} 