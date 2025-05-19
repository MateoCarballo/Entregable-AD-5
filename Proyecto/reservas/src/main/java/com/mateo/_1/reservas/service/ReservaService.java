package com.mateo._1.reservas.service;

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

    public String crearReserva(CrearReservaDTO crearReservaDTO) {
        //0 INICIALIZAR template para preguntar al microservicio de usuarios
        RestTemplate restTemplate = new RestTemplate();

        //1- Contruir un nuevo dto para validar credenciales
        UserNombreContrasenaDTO usuario = UserNombreContrasenaDTO.builder()
                .nombre(crearReservaDTO.getUsuarioNombre())
                .contrasena(crearReservaDTO.getUsuarioContrasena())
                .build();
        //2 Preguntar al microservicio si son validos o no
        ResponseEntity<Boolean> response = restTemplate.postForEntity(URL_VALIDAR_CREDENCIALES, usuario, Boolean.class);

        //3 Si son validos continua con el proceso si no lanzar una excepcion indicando que problema tenemos
        if (!Boolean.TRUE.equals(response.getBody())) {
           throw new CredencialesIncorrectosException("Los credenciales para el usuario " + crearReservaDTO.getUsuarioNombre() + " no son correctos revíselos");
        }

        //4 Encontrar el objeto habitacion que tiene ese id

        Habitacion habitacion = habitacionRepositoryImpl.findById(crearReservaDTO.getHabitacionId())
                .orElseThrow(()-> new HabitacionNotFoundException("El id de la habitacion no existe"));

        Reserva reserva = Reserva.builder()
                .usuarioId(crearReservaDTO.getUsuarioId())
                .habitacion(habitacion)
                .fechaInicio(crearReservaDTO.getFechaInicio())
                .fechaFin(crearReservaDTO.getFechaFin())
                .estado(crearReservaDTO.getEstado())
                .build();

        reservaRepositoryImpl.save(reserva);
    //TODO trabajando aqui !
        return "Reserva creada con exito!";
    }
}
