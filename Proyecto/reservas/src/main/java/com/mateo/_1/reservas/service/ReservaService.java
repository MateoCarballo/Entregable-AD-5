package com.mateo._1.reservas.service;

import com.mateo._1.reservas.dto.CrearReservaDTO;
import com.mateo._1.reservas.dto.UserNombreContrasenaDTO;
import com.mateo._1.reservas.entity.Habitacion;
import com.mateo._1.reservas.entity.Reserva;
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

    public void crearReserva(CrearReservaDTO crearReservaDTO) {
        //0 INICIALIZAR
        RestTemplate restTemplate = new RestTemplate();

        //1- Validar los credenciales que nos llegan del usuario
        UserNombreContrasenaDTO usuario = UserNombreContrasenaDTO.builder()
                .nombre(crearReservaDTO.getUsuarioNombre())
                .contrasena(crearReservaDTO.getUsuarioContrasena())
                .build();

        ResponseEntity<Boolean> response = restTemplate.postForEntity(URL_VALIDAR_CREDENCIALES, usuario, Boolean.class);
    //TODO trabajando aqui !
        return ResponseEntity.ok("Reserva creada con exito!");
    }
}
