package com.mateo._1.reservas.service;

import com.mateo._1.reservas.dto.ActualizarHabitacionDTO;
import com.mateo._1.reservas.dto.CrearHabitacionDTO;
import com.mateo._1.reservas.entity.Habitacion;
import com.mateo._1.reservas.entity.Hotel;
import com.mateo._1.reservas.exceptions.HabitacionNotFoundException;
import com.mateo._1.reservas.exceptions.HotelNotFoundException;
import com.mateo._1.reservas.repository.HabitacionRepository;
import com.mateo._1.reservas.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        Hotel hotel = hotelRepositoryImpl.findById(actualizarHabitacionDTO.getIdHotel())
                .orElseThrow(() -> new HotelNotFoundException("Hotel con ID " + actualizarHabitacionDTO.getIdHotel() + " no encontrado"));

        Habitacion habitacion = habitacionRepositoryImpl.findById(actualizarHabitacionDTO.getId())
                .orElseThrow(() -> new HabitacionNotFoundException("Habitacion con ID " + actualizarHabitacionDTO.getId() + " no encontrada"));

        habitacion.setNumeroHabitacion(actualizarHabitacionDTO.getNumeroHabitacion());
        habitacion.setTipo(actualizarHabitacionDTO.getTipo());
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

