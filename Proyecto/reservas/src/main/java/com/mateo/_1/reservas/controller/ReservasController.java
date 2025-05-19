package com.mateo._1.reservas.controller;

import com.mateo._1.reservas.dto.*;
import com.mateo._1.reservas.service.HabitacionService;
import com.mateo._1.reservas.service.HotelService;
import com.mateo._1.reservas.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Crear una nuevo habitación (crearHabitación):
        Se encargará de dar de alta una nueva habitación en un hotel.
        URL de ejecución: la ruta raíz del gestor de habitaciones.
        Mét0do de consulta: POST.
        Recibirá un objeto con la información de la habitación (numeroHabitacion, tipo, precio y idHotel)
        Devolverá una cadena indicando si la operación se completó correctamente o si hubo algún fallo.
     */
    @PostMapping("/habitacion/crear")
    public ResponseEntity<?> crearHabitacion(@RequestBody CrearHabitacionDTO crearHabitacionDTO) {
        return habitacionServiceImpl.crearHabitacion(crearHabitacionDTO);
    }

    /*
    Actualizar información de una habitación (actualizarHabitacion):
    Se encargará de actualizar los datos de una habitación en un hotel.
    URL de ejecución: la ruta raíz del gestor de habitaciones.
    Método de consulta: PATCH.
    Recibirá un objeto con la información de la habitación (id, numeroHabitacion, tipo, precio, idHotel y disponible)
    Devolverá una cadena indicando si la operación se completó correctamente o si hubo algún fallo.
     */

    @PatchMapping("/habitacion")
    public ResponseEntity<?> actualizarHabitacion(@RequestBody ActualizarHabitacionDTO actualizarHabitacionDTO) {
        return habitacionServiceImpl.actualizarHabitacion(actualizarHabitacionDTO);
    }

    @DeleteMapping("/habitacion/{id}")
    public ResponseEntity<?> eliminarHabitacion(@PathVariable int id) {
        return habitacionServiceImpl.eliminarHabitacion(id);
    }

    //HOTELES
    /*
    Crear un nuevo hotel (crearHotel):
    Se encargará de dar de alta un nuevo hotel.
    URL de ejecución: la ruta raíz del gestor de hoteles.
    Mét0do de consulta: POST.
    Recibirá un objeto con la información del hotel (nombre y direccion)
    Devolverá una cadena indicando si la operación se completó correctamente o si hubo algún fallo.
     */
    @PostMapping("/hotel")
    public ResponseEntity<?> crearHotel(@RequestBody CrearHotelDTO crearHotelDTO) {
        return hotelServiceImpl.crearHotel(crearHotelDTO);
    }

    /*
    Modificar información de un hotel (actualizarHotel):
    Se encargará de actualizar los datos de un hotel.
    URL de ejecución: la ruta raíz del gestor de hoteles.
    Mét0do de consulta: PATCH.
    Recibirá un objeto con la información de la habitación (id, nombre y direccion)
    Devolverá una cadena indicando si la operación se completó correctamente o si hubo algún fallo.
     */
    @PatchMapping("/hotel")
    public ResponseEntity<?> actualizarHotel(@RequestBody ActualizarHotelDTO actualizarHotelDTO) {
        return hotelServiceImpl.actualiazarHotel(actualizarHotelDTO);
    }

    /*
    Eliminar hotel (eliminarHotel):
    Se encargará de eliminar los datos de un hotel junto con todas sus habitaciones.
    URL de ejecución: la ruta raíz del gestor de hoteles.
    Mét0do de consulta: DELETE.
    Recibirá a través de la URL el identificador del hotel.
    Devolverá una cadena indicando si la operación se completó correctamente o si hubo algún fallo.
     */
    @DeleteMapping("/hotel/{id}")
    public ResponseEntity<?> eliminarHotel(@PathVariable int id) {
        return hotelServiceImpl.eliminarHotel(id);
    }

    /*
    Obtener el ID de un hotel a partir de su nombre (obtenerIdApartirNombre):
    Buscará el ID asociado al nombre del hotel.
    URL de ejecución: /id.
    Mét0do de consulta: POST.
    Recibirá a través de la URL el nombre del hotel.
    Devolverá una cadena indicando el ID del hotel en cuestión.
     */
    //TODO preguntar Jose, es necesario crear una dto para esto ??
    @PostMapping ("/hotel/id")
    public ResponseEntity<?> obtenerIdApartirNombre(@RequestBody ObtenerIdApartirNombreDTO obtenerIdApartirNombreDTO){
        return hotelServiceImpl.obtenerIdApartirNombre(obtenerIdApartirNombreDTO);
    }

    /*
    Obtener el nombre de un hotel a partir de su identificador (obtenerNombreAPartirId):
    Buscará el nombre asociado a a un ID.
    URL de ejecución: /nombre.
    Mét0do de consulta: POST.
    Recibirá a través de la URL el id del hotel.
    Devolverá una cadena indicando el nombre del hotel en cuestión.
     */

    @PostMapping("/hotel/nombre/{id}")
    public ResponseEntity<?> obtenerNombreAPartirId(@PathVariable int id){
        return hotelServiceImpl.obtenerNombreAPartirId(id);
    }

    //RESERVAS
    /*
    Crear reserva (crearReserva):
    Se encargará de crear una nueva reserva.
    URL de ejecución: la ruta raíz del microservicio.
    Mét0do de consulta: POST.
    Recibirá un objeto con la información de la reserva (fecha_inicio, fecha_fin y habitacion_id)
    Devolverá una cadena indicando si la operación se completó correctamente o si hubo algún fallo.
    */

    @PostMapping("/reservas")
    public ResponseEntity<?> crearReserva(@RequestBody CrearReservaDTO crearReservaDTO){
        reservaServiceImpl.crearReserva(crearReservaDTO);
        return ResponseEntity.ok("Reserva creada con exito!");
    }

}
