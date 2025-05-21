package com.mateo._1.reservas.controller;

import com.mateo._1.reservas.dto.*;
import com.mateo._1.reservas.service.HabitacionService;
import com.mateo._1.reservas.service.HotelService;
import com.mateo._1.reservas.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
@RequestMapping("/reservas")
public class ReservasController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String USUARIOS_SERVICE = "usuarios";

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
        if (!validarCredenciales(crearHabitacionDTO.getUsuario(), crearHabitacionDTO.getContrasena()))
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Los credenciales no son correctos.");
         return ResponseEntity.ok(habitacionServiceImpl.crearHabitacion(crearHabitacionDTO));
    }

    /*
    Actualizar información de una habitación (actualizarHabitacion):
    Se encargará de actualizar los datos de una habitación en un hotel.
    URL de ejecución: la ruta raíz del gestor de habitaciones.
    Mét0do de consulta: PATCH.
    Recibirá un objeto con la información de la habitación (id, numeroHabitacion, tipo, precio, idHotel y disponible)
    Devolverá una cadena indicando si la operación se completó correctamente o si hubo algún fallo.
     */

    @PatchMapping("/habitacion")
    public ResponseEntity<?> actualizarHabitacion(@RequestBody ActualizarHabitacionDTO actualizarHabitacionDTO) {
        if (!validarCredenciales(actualizarHabitacionDTO.getUsuario(), actualizarHabitacionDTO.getContrasena()))
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Los credenciales no son correctos.");
        ;
        return ResponseEntity.ok(habitacionServiceImpl.actualizarHabitacion(actualizarHabitacionDTO));
    }

    @DeleteMapping("/habitacion/{id}")
    public ResponseEntity<?> eliminarHabitacion(@PathVariable int id) {
        return ResponseEntity.ok(habitacionServiceImpl.eliminarHabitacion(id));
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
        if (!validarCredenciales(crearHotelDTO.getUsuario(), crearHotelDTO.getContrasena())) return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Los credenciales no son correctos.");
        return ResponseEntity.ok(hotelServiceImpl.crearHotel(crearHotelDTO));
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
        if (!validarCredenciales(actualizarHotelDTO.getUsuario(), actualizarHotelDTO.getContrasena()))
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Los credenciales no son correctos.");
        return ResponseEntity.ok(hotelServiceImpl.actualiazarHotel(actualizarHotelDTO));
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
        return ResponseEntity.ok(hotelServiceImpl.eliminarHotel(id));
    }

    /*
    Obtener el ID de un hotel a partir de su nombre (obtenerIdApartirNombre):
    Buscará el ID asociado al nombre del hotel.
    URL de ejecución: /id.
    Mét0do de consulta: POST.
    Recibirá a través de la URL el nombre del hotel.
    Devolverá una cadena indicando el ID del hotel en cuestión.
     */
    @PostMapping("/hotel/id/{nombre}")
    public ResponseEntity<?> obtenerIdApartirNombre(@PathVariable String nombre, @RequestBody UserNombreContrasenaDTO userNombreContrasenaDTO) {
        if (!validarCredenciales(userNombreContrasenaDTO.getNombre(), userNombreContrasenaDTO.getContrasena()))
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Los credenciales no son correctos.");
        return ResponseEntity.ok(hotelServiceImpl.obtenerIdApartirNombre(nombre));
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
    public ResponseEntity<?> obtenerNombreAPartirId(@PathVariable int id, @RequestBody UserNombreContrasenaDTO userNombreContrasenaDTO) {
        if (!validarCredenciales(userNombreContrasenaDTO.getNombre(), userNombreContrasenaDTO.getContrasena()))
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Los credenciales no son correctos.");
        return ResponseEntity.ok(hotelServiceImpl.obtenerNombreAPartirId(id));
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

    @PostMapping("/reserva")
    public ResponseEntity<?> crearReserva(@RequestBody CrearReservaDTOEntrada crearReservaDTOEntrada) {
        if (!validarCredenciales(crearReservaDTOEntrada.getUsuario(), crearReservaDTOEntrada.getContrasena())) return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Los credenciales no son correctos.");
        /*
        Por intentar reutilizar me contruyo una dto y le digo que me pregunte el id asociado al nombre para poder crear un nuevo DTO
        que pasaremos al service y nos creara la nueva entrada en la base de datos
         */
        int idUsuario = obtenerIdUsuario(crearDTOUserNombreContrasena(crearReservaDTOEntrada.getUsuario(), crearReservaDTOEntrada.getContrasena()));
        CrearReservaDTOSalida dtoSalida = CrearReservaDTOSalida.builder()
                .usuario_id(idUsuario)
                .habitacion_id(crearReservaDTOEntrada.getHabitacion_id())
                .fecha_inicio(crearReservaDTOEntrada.getFecha_inicio())
                .fecha_fin(crearReservaDTOEntrada.getFecha_fin())
                .build();
        return ResponseEntity.ok(reservaServiceImpl.crearReserva(dtoSalida));
    }

    /*
    Cambiar estado de una reserva (cambiarEstado):
    Se encargará de modificar el estado de una reserva.
    URL de ejecución: la ruta raíz del microservicio.
    Mét0do de consulta: PATCH.
    Recibirá un objeto con la información de la reserva (reserva_id y estado)
    Devolverá una cadena indicando si la operación se completó correctamente o si hubo algún fallo.
     */
    @PatchMapping("/reserva")
    public ResponseEntity<?> cambiarEstado(@RequestBody CambiarEstadoReservaDTO cambiarEstadoReservaDTO) {
        if (!validarCredenciales(cambiarEstadoReservaDTO.getUsuario(), cambiarEstadoReservaDTO.getContrasena()))
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Los credenciales no son correctos.");
        return ResponseEntity.ok(reservaServiceImpl.cambiarEstado(cambiarEstadoReservaDTO));
    }
    /*
    Listar reservas del usuario (listarReservasUsuario):
    Se encargará de listar las reservas que están asociadas al usuario.
    URL de ejecución: la ruta raíz del microservicio.
    Mét0do de consulta: GET.
    Solo recibirá la información de usuario y contraseña.
    Devolverá una lista con la información de las reservas (fecha_inicio, fecha_fin y habitacion_id).
     */

    @GetMapping("/reserva")
    public ResponseEntity<?> listarReservasUsuario(@RequestBody UserNombreContrasenaDTO userNombreContrasenaDTO) {
        if (!validarCredenciales(userNombreContrasenaDTO.getNombre(), userNombreContrasenaDTO.getContrasena())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Los credenciales no son correctos.");
        }
        int idUsuario = obtenerIdUsuario(userNombreContrasenaDTO);
        return ResponseEntity.ok(reservaServiceImpl.listarReservasPorUsuario(idUsuario));
    }

    /*
    Listar reservas según estado (listarReservasSegunEstado):
    Se encargará de listar todas las reservas que tengan un determinado estado
    independientemente del usuario que la haya hecho.
    URL de ejecución: la ruta raíz del microservicio.
    Mét0do de consulta: GET.
    Recibirá a través de la URL el estado de la habitación.
    Devolverá una lista con la información de las reservas (fecha_inicio, fecha_fin y habitacion_id).
     */
    @GetMapping("/reserva/{estado}")
    public ResponseEntity<?> listarReservasSegunEstado(@PathVariable(name = "estado") String estado) {
        if (!validarEstado(estado)) return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("El estado no está definido(Confirmada, Pendiente, Cancelada)");
        return ResponseEntity.ok(reservaServiceImpl.listarReservasPorEstado(estado));
    }

    private boolean validarEstado(String estado) {
        String[] posiblesEstados = {"Pendiente", "Confirmada", "Cancelada"};
        return Arrays.asList(posiblesEstados).contains(estado);
    }

    /*
    Comprobar reserva (checkReserva):
    Se encargará de validar si una reserva está asociada a un usuario y a un hotel en concreto.
    URL de ejecución: /check
    Mét0do de consulta: GET.
    Recibirá a través de la URL el idUsuario, el idHotel y el idReserva.
    Devolverá una booleano indicando si existe o si no.
    Este mét0do no recibirá la información de usuario y contraseña.
     */

    @GetMapping("/check/{idUsuario}-{idHotel}-{idReserva}")
    public ResponseEntity<?> checkReserva(
            @PathVariable(name = "idUsuario") int idUsuario,
            @PathVariable(name = "idHotel") int idHotel,
            @PathVariable(name = "idReserva") int idReserva) {

        CheckReservaDTO checkReservaDTO = new CheckReservaDTO();
        checkReservaDTO.setIdUsuario(idUsuario);
        checkReservaDTO.setIdReserva(idReserva);
        checkReservaDTO.setIdHotel(idHotel);
        boolean existeReserva = reservaServiceImpl
                .checkReserva(checkReservaDTO);
        return ResponseEntity.ok(existeReserva);
    }


    //###############################################################################################
    //                                          COSAS COMUNES
    //###############################################################################################

    private UserNombreContrasenaDTO crearDTOUserNombreContrasena(String nombre, String contrasena) {
        return UserNombreContrasenaDTO.builder()
                .nombre(nombre)
                .contrasena(contrasena)
                .build();
    }

    private boolean validarCredenciales(String nombre, String contrasena) {
        UserNombreContrasenaDTO comprobarCredencialesDTO = UserNombreContrasenaDTO.builder()
                .nombre(nombre)
                .contrasena(contrasena)
                .build();
        return validarEnMicroServicioUsuarios(comprobarCredencialesDTO);
    }

    //Pregunta a microservicio usuarios si los credenciales son validos (nombre de usuario y contraseña)
    private boolean validarEnMicroServicioUsuarios(UserNombreContrasenaDTO userNombreContrasenaDTO) {
        String url = "http://" + USUARIOS_SERVICE + "/usuarios/validar";
        ResponseEntity<Boolean> response = restTemplate.postForEntity(
                url, userNombreContrasenaDTO, Boolean.class
        );
        return Boolean.TRUE.equals(response.getBody());
    }

    //Pregunta a usuarios que id corresponde al nombre de usuario que le llega
    private int obtenerIdUsuario(UserNombreContrasenaDTO UserNombreContrasenaDTO) {
        String url = "http://" + USUARIOS_SERVICE + "/usuarios/obtenerId";
        ResponseEntity<UserNombreIdDTO> response = restTemplate.postForEntity(
                url, UserNombreContrasenaDTO, UserNombreIdDTO.class
        );
        return response.getBody().getId();
    }
}

