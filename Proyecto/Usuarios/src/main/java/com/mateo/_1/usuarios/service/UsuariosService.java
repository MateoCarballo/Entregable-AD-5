package com.mateo._1.usuarios.service;

import com.mateo._1.usuarios.dto.RegistroUsuarioDTO;
import com.mateo._1.usuarios.dto.UserCompleteDTO;
import com.mateo._1.usuarios.dto.UserNombreContrasenaDTO;
import com.mateo._1.usuarios.dto.UserNombreIdDTO;
import com.mateo._1.usuarios.entity.Usuario;
import com.mateo._1.usuarios.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuariosService {
    private final UsuariosRepository usuariosRepositoryImpl;

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuariosRepositoryImpl.findAll();
    }


    @Autowired
    public UsuariosService(UsuariosRepository usuariosRepositoryImpl) {
        this.usuariosRepositoryImpl = usuariosRepositoryImpl;
    }

    public String registrarUsuario(RegistroUsuarioDTO registroUsuarioDTO) {
        Usuario usuario = Usuario.builder()
                .nombre(registroUsuarioDTO.getNombre())
                .correo_electronico(registroUsuarioDTO.getCorreo_electronico())
                .direccion(registroUsuarioDTO.getDireccion())
                .contrasena(registroUsuarioDTO.getContrasena())
                .build();
        usuariosRepositoryImpl.save(usuario);
        return "Nuevo usuario guardado con id " + usuario.getId();
    }

    public String actualizarUsuario(UserCompleteDTO userCompleteDTO) {
        String cadenaResultado = "No existe el usuario que se desea modificar";
        Usuario usuario = usuariosRepositoryImpl.findById(userCompleteDTO.getId()).orElse(null);
        if (usuario != null) {
            //Cargamos los nuevos valoresd desde el DTO para modificar el usuario y lo guardamos nuevamente
            usuario.setNombre(userCompleteDTO.getNombre());
            usuario.setCorreo_electronico(userCompleteDTO.getCorreo_electronico());
            usuario.setDireccion(userCompleteDTO.getDireccion());
            usuario.setContrasena(userCompleteDTO.getContrasena());

            //Guardar el usuario directamente ya modificado
            usuariosRepositoryImpl.save(usuario);
            cadenaResultado = "Usuario modificado con exito";
        }
        return cadenaResultado;
    }

    public String eliminarUsuario(UserNombreContrasenaDTO userNombreContrasenaDto) {
        String response = "No se ha encontrado ningun usuario con este nombre y constrasena";
        Usuario usuario = usuariosRepositoryImpl.findByNombreAndContrasena(
                userNombreContrasenaDto.getNombre(),
                userNombreContrasenaDto.getContrasena()
        ).orElse(null);

        if (usuario != null) {
            usuariosRepositoryImpl.delete(usuario);
            response = "Usuario eliminado con exito";
        }
        return response;
    }

    public Usuario obtenerUsuarioPorId(int id) {
        return usuariosRepositoryImpl.findById(id).orElse(null);
    }

    public String obtenerUsuarioPorNombre(String nombre) {
        Usuario u = usuariosRepositoryImpl.findByNombre(nombre).orElse(null);
        if (u== null){
            return "No existe ningun usuario registrado con este nombre " + nombre;
        }
        return String.valueOf(u.getId());
    }

    public Usuario validarNombreConstrasena(String nombre, String clave) {
        return usuariosRepositoryImpl.findByNombreAndContrasena(nombre, clave).orElse(null);
    }

    public Usuario comprobarId(int id) {
        return usuariosRepositoryImpl.findById(id).orElse(null);
    }

    public boolean validarCredenciales(UserNombreContrasenaDTO userNombreContrasenaDto) {
        return usuariosRepositoryImpl
                .findByNombreAndContrasena(userNombreContrasenaDto.getNombre(), userNombreContrasenaDto.getContrasena())
                .isPresent();
    }

    public UserNombreIdDTO obtenerUserId(UserNombreContrasenaDTO userNombreContrasenaDTO) {
        Usuario usuario = usuariosRepositoryImpl.findByNombre(userNombreContrasenaDTO.getNombre()).orElse(null);
        if (usuario == null) return null;
        UserNombreIdDTO userToRepond = UserNombreIdDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .build();
        return userToRepond;
    }
    /*
        Usuario usuario = usuariosRepositoryImpl
                .findByNombreAndContrasena(userNombreContrasenaDto.getNombre(), userNombreContrasenaDto.getContrasena())
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario y contraseña no coinciden"));
         */
}
