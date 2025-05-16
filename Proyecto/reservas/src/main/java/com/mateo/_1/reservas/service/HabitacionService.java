package com.mateo._1.reservas.service;

import com.mateo._1.reservas.dto.CrearHabitacionDTO;
import com.mateo._1.reservas.entity.Habitacion;
import com.mateo._1.reservas.entity.Hotel;
import com.mateo._1.reservas.exceptions.HotelNotFoundException;
import com.mateo._1.reservas.repository.HabitacionRepository;
import com.mateo._1.reservas.repository.HotelRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitacionService {

    private HabitacionRepository habitacionRepositoryImpl;
    private HotelRepository hotelRepositoryImpl;

    @Autowired
    public HabitacionService(HabitacionRepository habitacionRepositoryImpl, HotelRepository hotelRepositoryImpl) {
        this.habitacionRepositoryImpl = habitacionRepositoryImpl;
        this.hotelRepositoryImpl = hotelRepositoryImpl;
    }

    public List<Habitacion> devolverTodos() {
        return habitacionRepositoryImpl.findAll();
    }

    public ResponseEntity<?> crearHabitacion(CrearHabitacionDTO crearHabitacionDTO) {
        Hotel hotel = hotelRepositoryImpl.findById(crearHabitacionDTO.getIdHotel())
                .orElseThrow(() -> new HotelNotFoundException("Hotel con ID " + crearHabitacionDTO.getIdHotel() + " no encontrado"));

        Habitacion habitacion = Habitacion.builder()
                .hotel(hotel)
                .numeroHabitacion(crearHabitacionDTO.getNumeroHabitacion()) // <- esto era obligatorio
                .tipo(crearHabitacionDTO.getTipo().toString())
                .precio(crearHabitacionDTO.getPrecio())
                .disponible(true)
                .build();

        habitacionRepositoryImpl.save(habitacion);

        return ResponseEntity.ok("Habitacion creada con éxito!");
    }

}

