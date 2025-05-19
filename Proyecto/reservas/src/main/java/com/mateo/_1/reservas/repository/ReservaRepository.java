package com.mateo._1.reservas.repository;

import com.mateo._1.reservas.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva,Integer> {

    List<Reserva> findAllByUsuarioId(int idUsuario);

    List<Reserva> findAllByEstado(String estado);

    Reserva findByReservaIdAndUsuarioIdAndHabitacion_habitacionId(int idReserva, int idUsuario, int idHabitacion);

    boolean existsByReservaIdAndUsuarioIdAndHabitacion_Hotel_Id(int idReserva, int idUsuario, int idHotel);
}
