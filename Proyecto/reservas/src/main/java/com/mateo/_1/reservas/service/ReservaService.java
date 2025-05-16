package com.mateo._1.reservas.service;

import com.mateo._1.reservas.entity.Reserva;
import com.mateo._1.reservas.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

    private ReservaRepository reservaRepositoryImpl;

    @Autowired
    public ReservaService(ReservaRepository reservaRepositoryImpl) {
        this.reservaRepositoryImpl = reservaRepositoryImpl;
    }

    public List<Reserva> devolverTodos(){
        return reservaRepositoryImpl.findAll();
    }
}
