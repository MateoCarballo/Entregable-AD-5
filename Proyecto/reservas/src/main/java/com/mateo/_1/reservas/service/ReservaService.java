package com.mateo._1.reservas.service;

import com.mateo._1.reservas.dto.CrearReservaDTO;
import com.mateo._1.reservas.entity.Habitacion;
import com.mateo._1.reservas.entity.Reserva;
import com.mateo._1.reservas.exceptions.HabitacionNotFoundException;
import com.mateo._1.reservas.repository.HabitacionRepository;
import com.mateo._1.reservas.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

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
        Habitacion habitacion = habitacionRepositoryImpl.findById(crearReservaDTO.getHabitacionId())
                .orElseThrow(() -> new HabitacionNotFoundException("La habitacion con id " + crearReservaDTO.getHabitacionId() + " no se encuentra. Imposible realizar la reserva"));

    }
}
