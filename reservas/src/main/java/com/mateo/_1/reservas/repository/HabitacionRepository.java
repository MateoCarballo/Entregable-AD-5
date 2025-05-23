package com.mateo._1.reservas.repository;

import com.mateo._1.reservas.entity.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Integer> {
    List<Integer> findAllByHotel_Id(int idHotel);
}
