package com.mateo._1.reservas.controller;

import com.mateo._1.reservas.dto.*;
import com.mateo._1.reservas.entity.Reserva;
import com.mateo._1.reservas.service.HabitacionService;
import com.mateo._1.reservas.service.HotelService;
import com.mateo._1.reservas.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservasController {
    //Antigua ruta sin tener eureka
    //private final String URL_VALIDAR_CREDENCIALES = "http://localhost:8502/usuarios/credenciales";

    //Usar eureka para facilitar las url sin tener que decirle la ip ni el puerto
    @Autowired
    private RestTemplate restTemplate;
    private static final String URL_VALIDAR_CREDENCIALES = "http://usuarios/usuarios/validar";

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
        if (!validarCredenciales(crearHabitacionDTO.getNombreUsuario(), crearHabitacionDTO.getContrasenaUsuario())) return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Los credenciales no son correctos.");
        return ResponseEntity.ok(habitacionServiceImpl.crearHabitacion(crearHabitacionDTO));
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
        if (!validarCredenciales(actualizarHabitacionDTO.getNombreUsuario(), actualizarHabitacionDTO.getContrasenaUsuario())) return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Los credenciales no son correctos.");;
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
        if (!validarCredenciales(crearHotelDTO.getNombreUsuario(), crearHotelDTO.getContrasenaUsuario())) return ResponseEntity
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
        if (!validarCredenciales(actualizarHotelDTO.getNombreUsuario(), actualizarHotelDTO.getContrasenaUsuario())) return ResponseEntity
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
    //TODO preguntar Jose, es necesario crear una dto para esto ??
    @PostMapping("/hotel/id")
    public ResponseEntity<?> obtenerIdApartirNombre(@RequestBody ObtenerIdApartirNombreDTO obtenerIdApartirNombreDTO) {
        if (!validarCredenciales(obtenerIdApartirNombreDTO.getNombreUsuario(), obtenerIdApartirNombreDTO.getContrasenaUsuario())) return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Los credenciales no son correctos.");
        return ResponseEntity.ok(hotelServiceImpl.obtenerIdApartirNombre(obtenerIdApartirNombreDTO));
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
    public ResponseEntity<?> obtenerNombreAPartirId(@PathVariable int id) {
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
    public ResponseEntity<?> crearReserva(@RequestBody CrearReservaDTO crearReservaDTO) {
        if (!validarCredenciales(crearReservaDTO.getNombreUsuario(), crearReservaDTO.getContrasenaUsuario())) return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Los credenciales no son correctos.");
        return ResponseEntity.ok(reservaServiceImpl.crearReserva(crearReservaDTO));
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
        if (!validarCredenciales(cambiarEstadoReservaDTO.getNombreUsuario(), cambiarEstadoReservaDTO.getContrasenaUsuario())) return ResponseEntity
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
    public ResponseEntity<?> listarReservasUsuario(@RequestBody UserNombreContrasenaDTO userNombreContrasenaDTO){
        if (!validarCredenciales(userNombreContrasenaDTO.getNombreUsuario(), userNombreContrasenaDTO.getContrasenaUsuario())){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Los credenciales no son correctos.");
        }
        return ResponseEntity.ok(reservaServiceImpl.listarReservasPorUsuario(userNombreContrasenaDTO));
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
    public ResponseEntity<?> listarReservasSegunEstado(@PathVariable(name = "estado") String estado){
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
            @PathVariable(name = "idReserva") int idReserva){

        CheckReservaDTO  checkReservaDTO = new CheckReservaDTO();
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


    private boolean validarCredenciales(String nombre, String  contrasena){
        UserNombreContrasenaDTO comprobarCredencialesDTO = UserNombreContrasenaDTO.builder()
                .nombreUsuario(nombre)
                .contrasenaUsuario(contrasena)
                .build();
        return validarEnMicroServicioUsuarios(comprobarCredencialesDTO);
    }
    private boolean validarEnMicroServicioUsuarios(UserNombreContrasenaDTO userNombreContrasenaDTO){
        ResponseEntity<Boolean> response = restTemplate.postForEntity(
                URL_VALIDAR_CREDENCIALES, userNombreContrasenaDTO, Boolean.class
        );
        return Boolean.TRUE.equals(response.getBody());
    }

   /* VALIDAR LOS CREDENCIALES SIN EUREKA

    private boolean validarEnMicroServicioUsuarios(UserNombreContrasenaDTO userNombreContrasenaDTO){
        //0 INICIALIZAR template para preguntar al microservicio de usuarios
        RestTemplate restTemplate = new RestTemplate();
        //1- Contruir un nuevo dto para validar credenciales
        UserNombreContrasenaDTO usuario = UserNombreContrasenaDTO.builder()
                .nombreUsuario(userNombreContrasenaDTO.getNombreUsuario())
                .contrasenaUsuario(userNombreContrasenaDTO.getContrasenaUsuario())
                .build();
        //2 Preguntar al microservicio si son validos o no
        ResponseEntity<Boolean> response = restTemplate.postForEntity(URL_VALIDAR_CREDENCIALES, usuario, Boolean.class);
        //Devolver si es o no valido
        return Boolean.TRUE.equals(response.getBody());
    }
    */
}

