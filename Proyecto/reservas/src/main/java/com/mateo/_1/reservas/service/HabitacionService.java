package com.mateo._1.reservas.service;

import com.mateo._1.reservas.dto.ActualizarHabitacionDTO;
import com.mateo._1.reservas.dto.CrearHabitacionDTO;
import com.mateo._1.reservas.entity.Habitacion;
import com.mateo._1.reservas.entity.Hotel;
import com.mateo._1.reservas.exceptions.HabitacionNotFoundException;
import com.mateo._1.reservas.exceptions.HotelNotFoundException;
import com.mateo._1.reservas.repository.HabitacionRepository;
import com.mateo._1.reservas.repository.HotelRepository;
import com.mateo._1.reservas.repository.ReservaRepository;
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

    public String crearHabitacion(CrearHabitacionDTO crearHabitacionDTO) {
        Hotel hotel = hotelRepositoryImpl.findById(crearHabitacionDTO.getIdHotel())
                .orElseThrow(() -> new HotelNotFoundException("Hotel con ID " + crearHabitacionDTO.getIdHotel() + " no encontrado"));

        Habitacion habitacion = Habitacion.builder()
                .hotel(hotel)
                .numeroHabitacion(crearHabitacionDTO.getNumeroHabitacion())
                .tipo(crearHabitacionDTO.getTipo().toString())
                .precio(crearHabitacionDTO.getPrecio())
                .disponible(true)
                .build();

        habitacionRepositoryImpl.save(habitacion);

        return "Habitacion creada con éxito!";
    }

    public String actualizarHabitacion(ActualizarHabitacionDTO actualizarHabitacionDTO) {
        /*
        Recibirá un objeto con la información de la habitación
        (id, numeroHabitacion, tipo, precio, idHotel y disponible)
         */

        //TODO jose preguntar. Que deberia hacer llamar entre services llamar directamente entre repositorios ??
        Hotel hotel = hotelRepositoryImpl.findById(actualizarHabitacionDTO.getIdHotel())
                .orElseThrow(() -> new HotelNotFoundException("Hotel con ID " + actualizarHabitacionDTO.getIdHotel() + " no encontrado"));


        Habitacion habitacion = habitacionRepositoryImpl.findById(actualizarHabitacionDTO.getId())
                .orElseThrow(() -> new HabitacionNotFoundException("Habitacion con ID " + actualizarHabitacionDTO.getId() + " no encontrada"));

        //TODO preguntar Jose Como puedo hacer que lance una excepcion si no mandamos un valor valido para el tipo de habitacion, que es un enum de Strings ?
        habitacion.setNumeroHabitacion(actualizarHabitacionDTO.getNumeroHabitacion());
        habitacion.setTipo(actualizarHabitacionDTO.getTipo().toString());
        habitacion.setPrecio(actualizarHabitacionDTO.getPrecio());
        habitacion.setHotel(hotel);
        habitacion.setDisponible(actualizarHabitacionDTO.isDisponible());

        habitacionRepositoryImpl.save(habitacion);

        return"Habitacion actualizada con éxito!";
    }

    public String eliminarHabitacion(int idHabitacion) {

        Habitacion habitacion = habitacionRepositoryImpl.findById(idHabitacion)
                .orElseThrow(() -> new HabitacionNotFoundException("Habitacion con ID " + idHabitacion + " no encontrada"));

        habitacionRepositoryImpl.delete(habitacion);
        return"Habitacion eliminada con éxito!";
    }
}

