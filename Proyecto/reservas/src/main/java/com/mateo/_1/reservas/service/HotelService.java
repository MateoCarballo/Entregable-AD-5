package com.mateo._1.reservas.service;

import com.mateo._1.reservas.entity.Hotel;
import com.mateo._1.reservas.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Hotel encontrarHotelPorId(int idHotel) {
        return hotelRepositoryImpl.findById(idHotel).orElse(null);
    }
}
