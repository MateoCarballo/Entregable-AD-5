package com.mateo._1.reservas.service;

import com.mateo._1.reservas.dto.ActualizarHotelDTO;
import com.mateo._1.reservas.dto.CrearHotelDTO;
import com.mateo._1.reservas.dto.ObtenerIdApartirNombreDTO;
import com.mateo._1.reservas.entity.Hotel;
import com.mateo._1.reservas.exceptions.HotelNotFoundException;
import com.mateo._1.reservas.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    private HotelRepository hotelRepositoryImpl;
    @Autowired
    public HotelService(HotelRepository hotelRepositoryImpl) {
        this.hotelRepositoryImpl = hotelRepositoryImpl;
    }

    public List<Hotel> devolverTodos() {
        return hotelRepositoryImpl.findAll();
    }
/*
    public Hotel encontrarHotelPorId(int idHotel) {
        return hotelRepositoryImpl.findById(idHotel)
                .orElseThrow(() -> new HotelNotFoundException("Hotel con ID " + idHotel + " no encontrado"));
        //TODO preguntar Jose aqui deberia tener solo 1 lanzamiento de la excepcion
        // y llamar entre services o llamar directamente al repo desde un service que no le corresponderia ??
    }
 */

    public String crearHotel(CrearHotelDTO crearHotelDTO) {
        hotelRepositoryImpl.save(new Hotel().builder()
                .nombre(crearHotelDTO.getNombre())
                .direccion(crearHotelDTO.getDireccion()).build());
        return "Hotel creado con exito!";
    }

    public String actualiazarHotel(ActualizarHotelDTO actualizarHotelDTO) {
        Hotel hotel = hotelRepositoryImpl.findById(actualizarHotelDTO.getId())
                .orElseThrow(() -> new HotelNotFoundException("Hotel con ID " + actualizarHotelDTO.getId() + " no encontrado"));

        hotel.setDireccion(actualizarHotelDTO.getDireccion());
        hotel.setNombre(actualizarHotelDTO.getNombre());

        hotelRepositoryImpl.save(hotel);

        return"Hotel actualizado con exito!";
    }

    public String eliminarHotel(int id) {
        Hotel hotel = hotelRepositoryImpl.findById(id)
                .orElseThrow(() -> new HotelNotFoundException("Hotel con ID " + id + " no encontrado"));
        hotelRepositoryImpl.delete(hotel);
        return "Hotel eliminado con exito!";
    }

    public String obtenerIdApartirNombre(ObtenerIdApartirNombreDTO obtenerIdApartirNombreDTO) {
        //TODO Preguntar Jose esta bien definir las personalizadas asi ?
        Hotel hotel =  (Hotel)hotelRepositoryImpl.findByNombre(obtenerIdApartirNombreDTO.getNombre())
                .orElseThrow(()-> new HotelNotFoundException("Hotel con nombre " + obtenerIdApartirNombreDTO.getNombre() + " no encontrado"));
        return  "El id " + hotel.getId() + " corresponder al hotel " + obtenerIdApartirNombreDTO.getNombre();
    }

    public String obtenerNombreAPartirId(int idHotel) {
        Hotel hotel = hotelRepositoryImpl.findById(idHotel)
                .orElseThrow(()-> new HotelNotFoundException("Hotel con id " + idHotel + " no encontrado"));
        hotelRepositoryImpl.save(hotel);
        return "El hotel con id " + hotel.getId() + " se llama " + hotel.getNombre();
    }
}
