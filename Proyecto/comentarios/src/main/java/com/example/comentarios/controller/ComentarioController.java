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


//    Crear comentario (crearComentario):
//
//    Se encargará de crear y almacenar el comentario de un usuario sobre una reserva en un determinado hotel.
//    Recibirá un objeto con la información del comentario (nombreHotel, id de reserva, puntuación y comentario)
//    Devolverá el mismo objeto recibido a modo de confirmación.
//    Consultará el microservicio de reservas para obtener el id de hotel a partir del nombreHotel.
//    Consultará el microservicio de usuarios para obtener el id de usuario a partir del nombre de usuario.
//    Deberá comprobar frente al microservicio reservas (mét0do checkReserva) si la combinación
//    (idUsuario - idHotel - idReserva) existe antes de poder crear el comentario.
//    Si el usuario ya hizo un comentario sobre esa combinación
//    (idUsuario - idHotel - idReserva) no se podrá realizar el comentario.

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
