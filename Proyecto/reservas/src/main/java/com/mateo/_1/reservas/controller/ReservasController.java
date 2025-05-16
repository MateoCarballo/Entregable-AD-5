package com.mateo._1.reservas.controller;

import com.mateo._1.reservas.dto.CrearHabitacionDTO;
import com.mateo._1.reservas.entity.Habitacion;
import com.mateo._1.reservas.entity.Hotel;
import com.mateo._1.reservas.entity.Reserva;
import com.mateo._1.reservas.service.HabitacionService;
import com.mateo._1.reservas.service.HotelService;
import com.mateo._1.reservas.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservasController {
    private final String OPERATION_ERROR_MSG = "No se ha podido realizar la accion";
    private final String OPERATION_COMPLETE_MSG = "Accion realizada con exito";

    private ReservaService reservaServiceImpl;
    private HotelService hotelServiceImpl;
    private HabitacionService habitacionServiceImpl;

    @Autowired
    public ReservasController(ReservaService reservaServiceImpl, HotelService hotelServiceImpl, HabitacionService habitacionServiceImpl) {
        this.reservaServiceImpl = reservaServiceImpl;
        this.hotelServiceImpl = hotelServiceImpl;
        this.habitacionServiceImpl = habitacionServiceImpl;
    }

    //Endpoint para confirmar la conexion correcta desde postman lista el contenido de cada una de las tablas
    @GetMapping("/test")
    public ResponseEntity<?> testController() {
        return ResponseEntity.ok("Todo ok");
    }

    // EndPoint para poder consultar todos los datos desde postman
    @GetMapping("/habitacion/All")
    public ResponseEntity<?> habitaciontest() {
        return ResponseEntity.ok(habitacionServiceImpl.devolverTodos());
    }

    @GetMapping("/hotel/All")
    public ResponseEntity<?> hotelTest() {
        return ResponseEntity.ok(hotelServiceImpl.devolverTodos());
    }

    @GetMapping("/reserva/All")
    public ResponseEntity<?> reservaTest() {
        return ResponseEntity.ok(reservaServiceImpl.devolverTodos());
    }

    //Puntos pedidos para este microservicio

    //HABITACIONES
    /*
        Recibirá un objeto con la información de la habitación (numeroHabitacion, tipo, precio y idHotel)
        Devolverá una cadena indicando si la operación se completó correctamente o si hubo algún fallo.
     */
    @PostMapping("/habitacion/crear")
    public ResponseEntity<?> crearHabitacion(@RequestBody CrearHabitacionDTO crearHabitacionDTO) {
        return habitacionServiceImpl.crearHabitacion(crearHabitacionDTO);
    }
}
