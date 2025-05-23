package com.mateo._1.usuarios.controller;

import com.mateo._1.usuarios.dto.RegistroUsuarioDTO;
import com.mateo._1.usuarios.dto.UserCompleteDTO;
import com.mateo._1.usuarios.dto.UserNombreContrasenaDTO;
import com.mateo._1.usuarios.dto.UserNombreIdDTO;
import com.mateo._1.usuarios.entity.Usuario;
import com.mateo._1.usuarios.service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {
    private final UsuariosService usuariosServiceImpl;

    @Autowired
    public UsuariosController(UsuariosService usuariosServiceImpl) {
        this.usuariosServiceImpl = usuariosServiceImpl;
    }

    //END POINTS UTILES PARA COMPROBACIONES PREVIAS
    @GetMapping("/todos")
    public ResponseEntity<?> obtenerTodosLosUsuarios() {
        return ResponseEntity.ok(usuariosServiceImpl.obtenerTodosLosUsuarios());
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("La aplicación está funcionando correctamente");
    }

    //END POINT PARA USAR DESDE RESERVAS Y VALIDAR LOS CREDENCIALES DE LOS USUARIOS
    //Es lo mismo que el otro pero para no interrumpir y que solo me devuelva un boolean le hago otro
    @PostMapping("/credenciales")
    public boolean credenciales(@RequestBody UserNombreContrasenaDTO userNombreContrasenaDto) {
        return usuariosServiceImpl.validarCredenciales(userNombreContrasenaDto);
    }

    //Este endpoint me devuelve el id necesario para insertarlo en la reserva del microservicio de reservas
    /*
    asi lo "llamo" desde el microservicio de reservas
    public int obtenerIdUsuario(UserNombreContrasenaDTO UserNombreContrasenaDTO){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserNombreIdDTO> response = restTemplate.postForEntity(URL_OBTENER_ID, UserNombreContrasenaDTO, UserNombreIdDTO.class);
        return response.getBody().getId();
    }
     */
    @PostMapping("/obtenerId")
    public UserNombreIdDTO obtenerId(@RequestBody UserNombreContrasenaDTO userNombreContrasenaDTO) {
        return usuariosServiceImpl.obtenerUserId(userNombreContrasenaDTO);
    }

    //TODO revisar aqui porque deberiamos usar una dto

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsusario(@RequestBody RegistroUsuarioDTO registroUsuarioDTO) {
        String cadenaRespuesta = usuariosServiceImpl.registrarUsuario(registroUsuarioDTO);
        return ResponseEntity.ok(cadenaRespuesta);
    }

    @PutMapping("/registrar")
    public ResponseEntity<String> actualizarUsusario(@RequestBody UserCompleteDTO u) {
        return ResponseEntity.ok(usuariosServiceImpl.actualizarUsuario(u));
    }

    @DeleteMapping("")
    public ResponseEntity<?> eliminarUsuario(@RequestBody UserNombreContrasenaDTO userNombreContrasenaDto) {
        return ResponseEntity.ok(usuariosServiceImpl.eliminarUsuario(userNombreContrasenaDto));
    }

    @PostMapping("/validar")
    public ResponseEntity<Boolean> validarUsuario(@RequestBody UserNombreContrasenaDTO userNombreContrasenaDto) {
        return ResponseEntity.ok(usuariosServiceImpl.validarNombreConstrasena(userNombreContrasenaDto.getNombre(), userNombreContrasenaDto.getContrasena()));
    }

    @GetMapping("/info/id/{id}")
    public ResponseEntity<String> obtenerInfoUsuarioPorId(@PathVariable int id) {
        String userName = "No se ha encontrado ningun usuario con el id proporcionado";
        Usuario user = usuariosServiceImpl.obtenerUsuarioPorId(id);
        if (user != null) {
            userName = user.getNombre();
        }
        return ResponseEntity.ok(userName);
    }

    @GetMapping("/info/nombre/{nombre}")
    public ResponseEntity<?> obtenerInfoUsuarioPorNombre(@PathVariable("nombre") String user_name) { //TODO revisar aqui si funciona llamandole difernete a cada cosa id -> UserId
        return ResponseEntity.ok(usuariosServiceImpl.obtenerUsuarioPorNombre(user_name));
    }

    @GetMapping("/checkIfExist/{id}")
    public ResponseEntity<Boolean> comprobarId(@PathVariable("id") int user_id) {
        boolean exist = false;
        Usuario user = usuariosServiceImpl.comprobarId(user_id);
        if (user != null) {
            exist = true;
        }
        return ResponseEntity.ok(exist);
    }
}