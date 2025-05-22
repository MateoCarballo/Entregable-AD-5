package com.example.comentarios.controller;

import com.example.comentarios.model.Comentario;
import com.example.comentarios.model.CreateComentarioInput;
import com.example.comentarios.model.CreateComentarioPayload;
import com.example.comentarios.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class ComentarioController {
    @Autowired
    private ComentarioService comentarioService;


    @MutationMapping
    public CreateComentarioPayload crearComentario(@Argument CreateComentarioInput input) {
        return comentarioService.crearComentario(input);
    }


    @MutationMapping
    public String eliminarComentarios() {
        return comentarioService.eliminarComentarios();
    }

    @QueryMapping
    public List<Comentario> getComentarios() {
        return comentarioService.getComentarios();
    }
}
/*
@QueryMapping
    public Comentario getComentario(@Argument String id) {
        return comentarioService.getComentario(id);
    }

    @MutationMapping
    public String eliminarComentarioDeUsuario() {
        return comentarioService.eliminarComentarioDeUsuario();
    }

    @QueryMapping
    public List<Comentario> listarComentariosHotel() {
        return comentarioService.listarComentariosHotel();
    }

    @QueryMapping
    public List<Comentario> listarComentariosUsuario() {
        return comentarioService.listarComentariosUsuario();
    }

    @QueryMapping
    public Comentario mostrarComentarioUsuarioReserva() {
        return comentarioService.mostrarComentarioUsuarioReserva();
    }

    @QueryMapping
    public Double puntuacionMediaHotel() {
        return comentarioService.puntuacionMediaHotel();
    }

    @QueryMapping
    public Double puntuacionesMediasUsuario() {
        return comentarioService.puntuacionesMediasUsuario();
    }

 */
