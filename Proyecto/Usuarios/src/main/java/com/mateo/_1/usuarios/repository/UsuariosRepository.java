package com.mateo._1.usuarios.repository;

import com.mateo._1.usuarios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByNombreAndContrasena(String name, String contrasena);

    Optional<Usuario> findByNombre(String nombre);

    Optional<Usuario> deleteByNombreAndContrasena(String nombre,String contrasena);

    @Query("SELECT u FROM Usuario u WHERE u.nombre = :nombre AND u.correo_electronico = :correoelectronico AND u.direccion = :direccion  AND u.contrasena = :contrasena")
    Optional<Usuario> findByCompleteUserDTO(
            @Param("nombre")String nombre,
            @Param("correo_electronico")String correoelectronico,
            @Param("direccion")String direccion,
            @Param("contrasena")String contrasena
    );
}