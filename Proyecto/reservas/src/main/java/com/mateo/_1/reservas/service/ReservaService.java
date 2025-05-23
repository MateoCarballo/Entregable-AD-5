package com.mateo._1.reservas.service;

import com.mateo._1.reservas.dto.*;
import com.mateo._1.reservas.entity.Habitacion;
import com.mateo._1.reservas.entity.Reserva;
import com.mateo._1.reservas.enums.EstadoReserva;
import com.mateo._1.reservas.repository.HabitacionRepository;
import com.mateo._1.reservas.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ReservaService {

    private ReservaRepository reservaRepositoryImpl;
    private HabitacionRepository habitacionRepositoryImpl;

    @Autowired
    public ReservaService(ReservaRepository reservaRepositoryImpl, HabitacionRepository habitacionRepositoryImpl) {
        this.reservaRepositoryImpl = reservaRepositoryImpl;
        this.habitacionRepositoryImpl = habitacionRepositoryImpl;
    }

    public List<Reserva> devolverTodos() {
        return reservaRepositoryImpl.findAll();
    }

    //TODO REVISAR ESTA BASURA
    public String crearReserva(CrearReservaDTOSalida crearReservaDTOSalida) {
        //2- Comprobar que la habitacion existe
        Habitacion habitacion = habitacionRepositoryImpl.findById(crearReservaDTOSalida.getHabitacion_id()).orElse(null);
        if (habitacion == null) {
            return "La habitacion con id " + crearReservaDTOSalida.getHabitacion_id() + " no existe en la DB, revisa los datos";
        }
        //3- Comprobar que las fechas estan bien escritas (no tener fecha fin antes que fecha inicio)
        if (!crearReservaDTOSalida.comprobarFechas()) { //Devuelve true si las fechas son coherentes
            return "Las fecha fin es previa a la fecha de inicio";
        }
        //4- Obtener desde el microservicio de usuarios el id asociado al nombre de usuario que llega
        Reserva reserva = Reserva.builder()
                .usuarioId(crearReservaDTOSalida.getUsuario_id())
                .habitacion(habitacion)
                .fechaInicio(crearReservaDTOSalida.getFecha_inicio())
                .fechaFin(crearReservaDTOSalida.getFecha_fin())
                .estado(crearReservaDTOSalida.getEstadoReserva().toString())
                .build();
        //5- Guardar la nueva reserva
        reservaRepositoryImpl.save(reserva);

        return "Reserva creada con éxito!";
    }

    public String cambiarEstado(CambiarEstadoReservaDTO cambiarEstadoReservaDTO) {
        //1- Comprobados credenciales antes de llegar aqui
        //2- Comprobar que la habitacion existe
        Reserva reserva = reservaRepositoryImpl.findById(cambiarEstadoReservaDTO.getReserva_id()).orElse(null);
        if (reserva == null) {
            return "La reserva con id " + cambiarEstadoReservaDTO.getReserva_id() + " no existe en la DB, revisa los datos";
        }
        String estadoAnterior = reserva.getEstado();
        /*
        POR SI USAMOS ENUMS
        EstadoReserva estadoNuevo;

        // 2- Validar que el estadoReserva recibido sea válido
        try {
            estadoNuevo = EstadoReserva.valueOf(estadoNuevoStr);
        } catch (IllegalArgumentException | NullPointerException e) {
            return "EstadoReserva inválido: " + estadoNuevoStr + ". Los valores válidos son: " + Arrays.toString(EstadoReserva.values());
        }
        */
        reserva.setEstado(cambiarEstadoReservaDTO.getEstado());

        //5- Guardar la nueva reserva
        reservaRepositoryImpl.save(reserva);
        return "El estado de la reserva ha cambiado de " + estadoAnterior  + " a " + reserva.getEstado();
    }

    public List<ListarReservasDTO> listarReservasPorUsuario(int idUsuario) {
        List<Reserva> reservas = reservaRepositoryImpl.findAllByUsuarioId(idUsuario);
        List<ListarReservasDTO> listadoReservasDTO = new ArrayList<>();
        for (Reserva r : reservas) {
            listadoReservasDTO.add(ListarReservasDTO.builder()
                    .habitacionId(r.getHabitacion().getHabitacionId())
                    .fechaInicio(r.getFechaInicio())
                    .fechaFin(r.getFechaFin())
                    .build());
        }
        return listadoReservasDTO;
    }

    public List<Reserva> listarReservasPorEstado(String estado) {
        return reservaRepositoryImpl.findAllByEstado(estado);
    }

    public boolean checkReserva(CheckReservaDTO checkReservaDTO) {
        return reservaRepositoryImpl
                .existsByReservaIdAndUsuarioIdAndHabitacion_Hotel_Id(
                        checkReservaDTO.getIdReserva(),
                        checkReservaDTO.getIdUsuario(),
                        checkReservaDTO.getIdHotel()
                );
    }
}
