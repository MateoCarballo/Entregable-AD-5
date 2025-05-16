package com.mateo._1.reservas.service;

import com.mateo._1.reservas.dto.ActualizarHabitacionDTO;
import com.mateo._1.reservas.dto.CrearHabitacionDTO;
import com.mateo._1.reservas.entity.Habitacion;
import com.mateo._1.reservas.entity.Hotel;
import com.mateo._1.reservas.entity.Reserva;
import com.mateo._1.reservas.exceptions.HabitacionNotFoundException;
import com.mateo._1.reservas.exceptions.HotelNotFoundException;
import com.mateo._1.reservas.repository.HabitacionRepository;
import com.mateo._1.reservas.repository.HotelRepository;
import com.mateo._1.reservas.repository.ReservaRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitacionService {

    private HabitacionRepository habitacionRepositoryImpl;
    private HotelRepository hotelRepositoryImpl;
    private ReservaRepository reservaRepositoryImpl;

    @Autowired
    public HabitacionService(HabitacionRepository habitacionRepositoryImpl, HotelRepository hotelRepositoryImpl, ReservaRepository reservaRepositoryImpl) {
        this.habitacionRepositoryImpl = habitacionRepositoryImpl;
        this.hotelRepositoryImpl = hotelRepositoryImpl;
        this.reservaRepositoryImpl = reservaRepositoryImpl;
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

    public ResponseEntity<?> actualizarHabitacion(ActualizarHabitacionDTO actualizarHabitacionDTO) {
        //1. Encontrar el hotel como objeto.
        //2. No llega reserva para poder modificarla
        //3. Modificar todos los campos que nos llegan menos nuestro propio id

        Hotel hotel = hotelRepositoryImpl.findById(actualizarHabitacionDTO.getIdHotel())
                .orElseThrow(() -> new HotelNotFoundException("Hotel con ID " + actualizarHabitacionDTO.getIdHotel() + " no encontrado"));


        Habitacion habitacion = habitacionRepositoryImpl.findById(actualizarHabitacionDTO.getId())
                .orElseThrow(() -> new HabitacionNotFoundException("Habitacion con ID " + actualizarHabitacionDTO.getId() + " no encontrada"));

        habitacion.setNumeroHabitacion(actualizarHabitacionDTO.getNumeroHabitacion());
        habitacion.setHotel(hotel);
        habitacion.setTipo(actualizarHabitacionDTO.getTipo().toString());
        habitacion.setPrecio(actualizarHabitacionDTO.getPrecio());
        habitacion.setDisponible(actualizarHabitacionDTO.isDisponible());

        habitacionRepositoryImpl.save(habitacion);

        return ResponseEntity.ok("Habitacion actualizada con éxito!");
    }
}

