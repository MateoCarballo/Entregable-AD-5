package com.mateo._1.reservas.service;

import com.mateo._1.reservas.dto.CambiarEstadoReservaDTO;
import com.mateo._1.reservas.dto.CrearReservaDTO;
import com.mateo._1.reservas.dto.UserNombreContrasenaDTO;
import com.mateo._1.reservas.dto.UserNombreIdDTO;
import com.mateo._1.reservas.entity.Habitacion;
import com.mateo._1.reservas.entity.Reserva;
import com.mateo._1.reservas.repository.HabitacionRepository;
import com.mateo._1.reservas.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ReservaService {
    private final String URL_VALIDAR_CREDENCIALES = "http://localhost:8502/usuarios/credenciales";
    private final String URL_OBTENER_ID = "http://localhost:8502/usuarios/obtenerId";

    private ReservaRepository reservaRepositoryImpl;
    private HabitacionRepository habitacionRepositoryImpl;

    @Autowired
    public ReservaService(ReservaRepository reservaRepositoryImpl, HabitacionRepository habitacionRepositoryImpl) {
        this.reservaRepositoryImpl = reservaRepositoryImpl;
        this.habitacionRepositoryImpl = habitacionRepositoryImpl;
    }

    public List<Reserva> devolverTodos(){
        return reservaRepositoryImpl.findAll();
    }

    //TODO REVISAR ESTA BASURA
    public String crearReserva(CrearReservaDTO crearReservaDTO) {
        //1- Comprobar que los credenciales son correctos
        UserNombreContrasenaDTO comprobarCredencialesDTO = UserNombreContrasenaDTO.builder()
                .nombre(crearReservaDTO.getUsuarioNombre())
                .contrasena(crearReservaDTO.getUsuarioContrasena())
                .build();
        if (!comprobarCredenciales(comprobarCredencialesDTO)) {
            return ("Los credenciales no coinciden");
        }
        //2- Comprobar que la habitacion existe
        Habitacion habitacion = habitacionRepositoryImpl.findById(crearReservaDTO.getHabitacionId()).orElse(null);
        if (habitacion == null) {
            return "La habitacion con id " + crearReservaDTO.getHabitacionId() + " no existe en la DB, revisa los datos";
        }
        //3- Comprobar que las fechas estan bien escritas (no tener fecha fin antes que fecha inicio)
        if (!crearReservaDTO.comprobarFechas()){ //Devuelve true si las fechas son coherentes
            return "Las fecha fin es previa a la fecha de inicio";
        }
        //4- Obtener desde el microservicio de usuarios el id asociado al nombre de usuario que llega
        Reserva reserva = Reserva.builder()
                .usuarioId(obtenerIdUsuario(comprobarCredencialesDTO))
                .habitacion(habitacion)
                .fechaInicio(crearReservaDTO.getFechaInicio())
                .fechaFin(crearReservaDTO.getFechaFin())
                .estado("Pendiente")
                .build();
        //5- Guardar la nueva reserva
        reservaRepositoryImpl.save(reserva);

       return "Reserva creada con éxito!";
    }

    public String cambiarEstado(CambiarEstadoReservaDTO cambiarEstadoReservaDTO) {
        //1- Comprobar que los credenciales son correctos
        UserNombreContrasenaDTO userNombreContrasenaDTO = UserNombreContrasenaDTO.builder()
                .nombre(cambiarEstadoReservaDTO.getNombre())
                .contrasena(cambiarEstadoReservaDTO.getContrasena())
                .build();
        if (!comprobarCredenciales(userNombreContrasenaDTO)) {
            return ("Los credenciales no coinciden");
        }
        //2- Comprobar que la habitacion existe
        Reserva reserva = reservaRepositoryImpl.findById(cambiarEstadoReservaDTO.getReserva_id()).orElse(null);
        if (reserva == null) {
            return "La reserva con id " +cambiarEstadoReservaDTO.getReserva_id() + " no existe en la DB, revisa los datos";
        }
        //4- Obtener desde el microservicio de usuarios el id asociado al nombre de usuario que llega
        String[] posiblesEstados = {"Pendiente", "Confirmada", "Cancelada"};
        String estadoAnterior = reserva.getEstado();
       if (!Arrays.asList(posiblesEstados).contains(cambiarEstadoReservaDTO.getEstadoReserva())) return "El nuevo estado no es valido";
        reserva.setEstado(cambiarEstadoReservaDTO.getEstadoReserva());
        return "El estado de la reserva ha cambiado de " + estadoAnterior + " a " + reserva.getEstado();
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
        //Devolver si es o no valido
        return Boolean.TRUE.equals(response.getBody());
    }
    public int obtenerIdUsuario(UserNombreContrasenaDTO UserNombreContrasenaDTO){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserNombreIdDTO> response = restTemplate.postForEntity(URL_OBTENER_ID, UserNombreContrasenaDTO, UserNombreIdDTO.class);
        return response.getBody().getId();
    }


}
