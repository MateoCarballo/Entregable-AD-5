package com.mateo._1.usuarios.service;

import com.mateo._1.usuarios.entity.UserCompleteDTO;
import com.mateo._1.usuarios.entity.UserNombreContrasenaDTO;
import com.mateo._1.usuarios.entity.Usuario;
import com.mateo._1.usuarios.exceptions.UsuarioNotFoundException;
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

    public String registrarUsuario(Usuario u) {
        Usuario usuarioGuardado = usuariosRepositoryImpl.save(u);
        return "Nuevo usuario guardado con id " + usuarioGuardado.getId();
    }

    public String actualizarUsuario(UserCompleteDTO userCompleteDTO) {
        String cadenaResultado = "No existe el usuario que se desea modificar";
        Usuario usuario = usuariosRepositoryImpl.findById(userCompleteDTO.getU_id()).orElse(null);
        if (usuario != null) {
            //Cargamos los nuevos valoresd desde el DTO para modificar el usuario y lo guardamos nuevamente
            usuario.setNombre(userCompleteDTO.getU_nombre());
            usuario.setCorreo_electronico(userCompleteDTO.getU_correo_electronico());
            usuario.setDireccion(userCompleteDTO.getU_direccion());
            usuario.setContrasena(userCompleteDTO.getU_contrasena());

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

    public Usuario obtenerUsuarioPorNombre(String nombre) {
        return usuariosRepositoryImpl.findByNombre(nombre).orElse(null);
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
    /*
        Usuario usuario = usuariosRepositoryImpl
                .findByNombreAndContrasena(userNombreContrasenaDto.getNombre(), userNombreContrasenaDto.getContrasena())
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario y contraseña no coinciden"));
         */
}
