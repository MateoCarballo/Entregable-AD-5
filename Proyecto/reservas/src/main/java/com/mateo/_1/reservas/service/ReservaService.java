package com.mateo._1.reservas.service;

import com.mateo._1.reservas.dto.CambiarEstadoReservaDTO;
import com.mateo._1.reservas.dto.CrearReservaDTO;
import com.mateo._1.reservas.dto.UserNombreContrasenaDTO;
import com.mateo._1.reservas.entity.Habitacion;
import com.mateo._1.reservas.entity.Reserva;
import com.mateo._1.reservas.exceptions.CredencialesIncorrectosException;
import com.mateo._1.reservas.exceptions.HabitacionNotFoundException;
import com.mateo._1.reservas.repository.HabitacionRepository;
import com.mateo._1.reservas.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ReservaService {
    private final String URL_VALIDAR_CREDENCIALES = "http://localhost:8502/usuarios/validarCredenciales";

    private ReservaRepository reservaRepositoryImpl;
    private HabitacionRepository habitacionRepositoryImpl;

    @Autowired
    public ReservaService(ReservaRepository reservaRepositoryImpl) {
        this.reservaRepositoryImpl = reservaRepositoryImpl;
    }

    public List<Reserva> devolverTodos(){
        return reservaRepositoryImpl.findAll();
    }

    //TODO REVISAR ESTA BASURA
    public String crearReserva(CrearReservaDTO crearReservaDTO) {
        UserNombreContrasenaDTO comprobarCredencialesDTO = UserNombreContrasenaDTO.builder()
                .nombre(crearReservaDTO.getUsuarioNombre())
                .contrasena(crearReservaDTO.getUsuarioContrasena())
                .build();
        if (!comprobarCredenciales(comprobarCredencialesDTO)) {
            return ("Los credenciales no coinciden");
        }

        Habitacion habitacion = habitacionRepositoryImpl.findById(crearReservaDTO.getHabitacionId())
                .orElseThrow(()-> new HabitacionNotFoundException("El id de la habitacion no existe"));

        //DEBERIAMOS COMPROBAR QUE LAS FECHAS ESTEN BIEN COLOCADAS
        if (!crearReservaDTO.comprobarFechas()){ //Devuelve true si las fechas son coherentes
            return "Las fecha fin es previa a la fecha de inicio";
        }

        Reserva reserva = Reserva.builder()
                .usuarioId(crearReservaDTO.getUsuarioId())
                .habitacion(habitacion)
                .fechaInicio(crearReservaDTO.getFechaInicio())
                .fechaFin(crearReservaDTO.getFechaFin())
                .estado(crearReservaDTO.getEstado())
                .build();

        reservaRepositoryImpl.save(reserva);
       return "Reserva creada con éxito!";
    }

    public String cambiarEstado(CambiarEstadoReservaDTO cambiarEstadoReservaDTO) {
        //Creamos un nuevo DTO para comprobar los credenciales en usuarios
        UserNombreContrasenaDTO comprobarCredencialesDTO = UserNombreContrasenaDTO.builder()
                .nombre(cambiarEstadoReservaDTO.getNombre())
                .contrasena(cambiarEstadoReservaDTO.getContrasena())
                .build();
        if (!comprobarCredenciales(comprobarCredencialesDTO)) {
            return ("Los credenciales no coinciden");
        }

        Reserva reserva = reservaRepositoryImpl.findById(cambiarEstadoReservaDTO.getReserva_id()).orElse(null);
        if (reserva == null) return "No se ha encontrado ninguna reserva con el id indicado "  + cambiarEstadoReservaDTO.getReserva_id();

        reserva.setEstado(cambiarEstadoReservaDTO.getEstadoReserva());
        reservaRepositoryImpl.save(reserva);
        return "El estado de la reserva ha cambiado de " + "" + " a " + "";
    }


    /*
    ##########################################################################
    METODO PARA COMPROBAR LOS CREDENCIALES CONTRA EL MICROSERVICIO DE USUARIOS
    ##########################################################################
     */

    public boolean comprobarCredenciales(UserNombreContrasenaDTO userNombreContrasenaDTO){
        //0 INICIALIZAR template para preguntar al microservicio de usuarios
        RestTemplate restTemplate = new RestTemplate();

        //1- Contruir un nuevo dto para validar credenciales
        UserNombreContrasenaDTO usuario = UserNombreContrasenaDTO.builder()
                .nombre(userNombreContrasenaDTO.getNombre())
                .contrasena(userNombreContrasenaDTO.getContrasena())
                .build();
        //2 Preguntar al microservicio si son validos o no
        ResponseEntity<Boolean> response = restTemplate.postForEntity(URL_VALIDAR_CREDENCIALES, usuario, Boolean.class);

        //3 Si son validos continua con el proceso si no lanzar una excepcion indicando que problema tenemos
        if (!Boolean.TRUE.equals(response.getBody())) {
            throw new CredencialesIncorrectosException("Error al comprobar los credenciales del usuario " + userNombreContrasenaDTO.getNombre() + " revíselos");
        }
        return Boolean.TRUE.equals(response.getBody());
    }
}
