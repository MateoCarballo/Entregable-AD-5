package com.example.comentarios.service;

import com.example.comentarios.model.Comentario;
import com.example.comentarios.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComentarioService {
    @Autowired
    private ComentarioRepository comentarioRepository;
    
    @Autowired
    private RestTemplate restTemplate;

    private boolean validarUsuario(String nombre, String contrasena) {
        String url = "http://usuarios/usuarios/validar";
        // Implementar la llamada al servicio de usuarios
        return true; // Temporal
    }

    private Integer obtenerIdUsuario(String nombre) {
        String url = "http://usuarios/usuarios/info/nombre/" + nombre;
        // Implementar la llamada al servicio de usuarios
        return 1; // Temporal
    }

    private Integer obtenerIdHotel(String nombreHotel) {
        String url = "http://reservas/reservas/hotel/id";
        // Implementar la llamada al servicio de reservas
        return 1; // Temporal
    }

    private boolean validarReserva(Integer usuarioId, Integer hotelId, Integer reservaId) {
        String url = String.format("http://reservas/reservas/check?idUsuario=%d&idHotel=%d&idReserva=%d", 
            usuarioId, hotelId, reservaId);
        // Implementar la llamada al servicio de reservas
        return true; // Temporal
    }

    public Comentario crearComentario(String nombreHotel, Integer reservaId, Double puntuacion, 
                                    String comentarioTexto, String usuario, String contrasena) {
        if (!validarUsuario(usuario, contrasena)) {
            throw new RuntimeException("Usuario no válido");
        }

        Integer usuarioId = obtenerIdUsuario(usuario);
        Integer hotelId = obtenerIdHotel(nombreHotel);

        if (!validarReserva(usuarioId, hotelId, reservaId)) {
            throw new RuntimeException("Reserva no válida");
        }

        if (comentarioRepository.existsByUsuarioIdAndHotelIdAndReservaId(usuarioId, hotelId, reservaId)) {
            throw new RuntimeException("Ya existe un comentario para esta reserva");
        }

        Comentario comentario = new Comentario();
        comentario.setUsuarioId(usuarioId);
        comentario.setHotelId(hotelId);
        comentario.setReservaId(reservaId);
        comentario.setPuntuacion(puntuacion);
        comentario.setComentario(comentarioTexto);
        comentario.setFechaCreacion(LocalDateTime.now());

        return comentarioRepository.save(comentario);
    }

    public String eliminarComentarios() {
        comentarioRepository.deleteAll();
        return "Todos los comentarios han sido eliminados";
    }

    public String eliminarComentarioDeUsuario(String id, String usuario, String contrasena) {
        if (!validarUsuario(usuario, contrasena)) {
            throw new RuntimeException("Usuario no válido");
        }

        comentarioRepository.deleteById(id);
        return "Comentario eliminado correctamente";
    }

    public List<Comentario> listarComentariosHotel(String nombreHotel, String usuario, String contrasena) {
        if (!validarUsuario(usuario, contrasena)) {
            throw new RuntimeException("Usuario no válido");
        }

        Integer hotelId = obtenerIdHotel(nombreHotel);
        return comentarioRepository.findByHotelId(hotelId);
    }

    public List<Comentario> listarComentariosUsuario(String usuario, String contrasena) {
        if (!validarUsuario(usuario, contrasena)) {
            throw new RuntimeException("Usuario no válido");
        }

        Integer usuarioId = obtenerIdUsuario(usuario);
        return comentarioRepository.findByUsuarioId(usuarioId);
    }

    public Comentario mostrarComentarioUsuarioReserva(Integer reservaId, String usuario, String contrasena) {
        if (!validarUsuario(usuario, contrasena)) {
            throw new RuntimeException("Usuario no válido");
        }

        Integer usuarioId = obtenerIdUsuario(usuario);
        return comentarioRepository.findByUsuarioIdAndReservaId(usuarioId, reservaId);
    }

    public Double puntuacionMediaHotel(String nombreHotel, String usuario, String contrasena) {
        if (!validarUsuario(usuario, contrasena)) {
            throw new RuntimeException("Usuario no válido");
        }

        Integer hotelId = obtenerIdHotel(nombreHotel);
        List<Comentario> comentarios = comentarioRepository.findByHotelId(hotelId);
        
        if (comentarios.isEmpty()) {
            return 0.0;
        }

        return comentarios.stream()
                .mapToDouble(Comentario::getPuntuacion)
                .average()
                .orElse(0.0);
    }

    public Double puntuacionesMediasUsuario(String usuario, String contrasena) {
        if (!validarUsuario(usuario, contrasena)) {
            throw new RuntimeException("Usuario no válido");
        }

        Integer usuarioId = obtenerIdUsuario(usuario);
        List<Comentario> comentarios = comentarioRepository.findByUsuarioId(usuarioId);
        
        if (comentarios.isEmpty()) {
            return 0.0;
        }

        return comentarios.stream()
                .mapToDouble(Comentario::getPuntuacion)
                .average()
                .orElse(0.0);
    }
} 