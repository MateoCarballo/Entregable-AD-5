package com.example.comentarios.controller;

import com.example.comentarios.model.Comentario;
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
    public Comentario crearComentario(@Argument ComentarioInput input) {
        return comentarioService.crearComentario(
            input.getNombreHotel(),
            input.getReservaId(),
            input.getPuntuacion(),
            input.getComentario(),
            input.getUsuario(),
            input.getContrasena()
        );
    }

    @MutationMapping
    public String eliminarComentarios() {
        return comentarioService.eliminarComentarios();
    }

    @MutationMapping
    public String eliminarComentarioDeUsuario(@Argument String id, 
                                            @Argument String usuario, 
                                            @Argument String contrasena) {
        return comentarioService.eliminarComentarioDeUsuario(id, usuario, contrasena);
    }

    @QueryMapping
    public List<Comentario> listarComentariosHotel(@Argument String nombreHotel, 
                                                  @Argument String usuario, 
                                                  @Argument String contrasena) {
        return comentarioService.listarComentariosHotel(nombreHotel, usuario, contrasena);
    }

    @QueryMapping
    public List<Comentario> listarComentariosUsuario(@Argument String usuario, 
                                                    @Argument String contrasena) {
        return comentarioService.listarComentariosUsuario(usuario, contrasena);
    }

    @QueryMapping
    public Comentario mostrarComentarioUsuarioReserva(@Argument Integer reservaId, 
                                                     @Argument String usuario, 
                                                     @Argument String contrasena) {
        return comentarioService.mostrarComentarioUsuarioReserva(reservaId, usuario, contrasena);
    }

    @QueryMapping
    public Double puntuacionMediaHotel(@Argument String nombreHotel, 
                                     @Argument String usuario, 
                                     @Argument String contrasena) {
        return comentarioService.puntuacionMediaHotel(nombreHotel, usuario, contrasena);
    }

    @QueryMapping
    public Double puntuacionesMediasUsuario(@Argument String usuario, 
                                          @Argument String contrasena) {
        return comentarioService.puntuacionesMediasUsuario(usuario, contrasena);
    }
}

record ComentarioInput(
    String nombreHotel,
    Integer reservaId,
    Double puntuacion,
    String comentario,
    String usuario,
    String contrasena
) {} 