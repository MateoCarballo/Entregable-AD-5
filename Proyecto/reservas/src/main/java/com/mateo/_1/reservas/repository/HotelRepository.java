package com.mateo._1.reservas.repository;

import com.mateo._1.reservas.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Integer> {

    Optional<Object> findByNombre(String nombre);
}
