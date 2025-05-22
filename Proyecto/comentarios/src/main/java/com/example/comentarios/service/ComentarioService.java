package com.example.comentarios.service;

import com.example.comentarios.model.*;
import com.example.comentarios.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComentarioService {
    private static final Logger log = LoggerFactory.getLogger(ComentarioService.class);

    @Autowired // ¡Inyecta el RestTemplate!
    private RestTemplate restTemplate;
    @Autowired
    private ComentarioRepository comentarioRepository;

    //    Consultará el microservicio de reservas para obtener el id de hotel a partir del nombreHotel.
    //    Consultará el microservicio de usuarios para obtener el id de usuario a partir del nombre de usuario.
    //    Deberá comprobar frente al microservicio reservas (mét0do checkReserva) si la combinación
    //    (idUsuario - idHotel - idReserva) existe antes de poder crear el comentario.
    //    Si el usuario ya hizo un comentario sobre esa combinación
    //    (idUsuario - idHotel - idReserva) no se podrá realizar el comentario.

    public CreateComentarioPayload crearComentario(CreateComentarioInput input) {
        //credenciales DTO y configuración inicial de URL
       /* Asi sin usar lo que esta en el paquete config

        //TODO Puedo crear una sola ? de ser asi por que es mejor inyectarla ?
        RestTemplate restTemplate = new RestTemplate();

        // TODO revisar la ruta del servicio
        String urlMicroservicioUsuarios = "http://localhost:8502/usuarios";
        String endpointObtenerId = "/obtenerId";

        // TODO revisar la ruta del servicio
        String urlMicroservicioReservas = "http://localhost:8501/reservas";
        String endpointObtenerIdPorNombre = "/hotel/id/%s".formatted(input.getNombreHotel());
        String endPointCheckReserva = "/reservas/check/%d-%d-%d";
        */
        // Aqui como hacerlo con el restTemplate con loadbalance
        // Usa los nombres de los servicios registrados en Eureka
        String urlMicroservicioUsuarios = "http://usuarios"; // ¡Aquí el cambio!
        String endpointObtenerId = "/usuarios/obtenerId"; // Este path sigue siendo el de tu controlador de usuarios

        String urlMicroservicioReservas = "http://reservas"; // ¡Aquí el cambio!
        String endpointObtenerIdPorNombre = "/reservas/hotel/id/%s".formatted(input.getNombreHotel()); // Asegúrate de que el path base del controlador de Reservas sea /reservas
        String endPointCheckReserva = "/reservas/check/%d-%d-%d";


        int idUsuario;
        int idHotel;
        boolean checkReserva;

        UserNombreContrasenaDTO credenciales = UserNombreContrasenaDTO.builder()
                .nombre(input.getUsuario())
                .contrasena(input.getContrasena())
                .build();

        try {
            //1. Obtener el id del usuario llamando al microservicio de usuarios @PostMapping("/obtenerId")
            ResponseEntity<UserNombreIdDTO> responseUserId = restTemplate
                    .postForEntity(urlMicroservicioUsuarios + endpointObtenerId, credenciales, UserNombreIdDTO.class);
            idUsuario = responseUserId.getBody().getId();
        } catch (HttpClientErrorException exception) {
            log.error("Error al buscar el id del usuario", exception);
            return new CreateComentarioPayload("Error al buscar el id del usuario", new Comentario());
        }

        try {
            //2. Obtener el idHotel llamando al microservicio de reservas @PostMapping("/hotel/id/{nombre}")
            ResponseEntity<String> responseHotelId = restTemplate
                    .postForEntity(urlMicroservicioReservas + endpointObtenerIdPorNombre, credenciales, String.class);
            idHotel = Integer.parseInt(responseHotelId.getBody().toString());
        } catch (HttpClientErrorException exception) {
            log.error("Error al buscar el id del usuario", exception);
            return new CreateComentarioPayload("Error al buscar el id del hotel", new Comentario());
        }

        try {
            //3.1. Comprobar en reservas si la combinacion es correcta /check/{idUsuario}-{idHotel}-{idReserva}"
            ResponseEntity<Boolean> responseCheckReserva = restTemplate
                    .postForEntity(urlMicroservicioReservas + endPointCheckReserva.formatted(idUsuario, idHotel, input.getIdReserva()), null, Boolean.class);
            checkReserva = responseCheckReserva.getBody();
            if (!checkReserva) return new CreateComentarioPayload("Los ids no coinciden con ninguna reserva",new Comentario());
        } catch (HttpClientErrorException exception) {
            log.error("Error al buscar el id del usuario", exception);
            return new CreateComentarioPayload("Error al buscar al comprobar la reserva", new Comentario());
        }

        Comentario comentarioEncontrado = comentarioRepository.findByUsuarioIdAndHotelIdAndReservaId(idUsuario, idHotel, input.getIdReserva()); // Todo puedo usar las construcciones con formato como desde MySQL

        if (comentarioEncontrado != null) {
            return new CreateComentarioPayload("Ya tienes un comentario creado sobre esta reserva", comentarioEncontrado);
        }

        Comentario comentarioNuevo = Comentario.builder()
                .usuarioId(idUsuario)
                .hotelId(idHotel)
                .reservaId(input.getIdReserva())
                .puntuacion(input.getPuntuacion())
                .comentario(input.getComentario())
                .fechaCreacion(LocalDateTime.now())
                .build();

        Comentario comentarioGuardado = comentarioRepository.save(comentarioNuevo);

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