package com.mateo._1.usuarios.repository;

import com.mateo._1.usuarios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNombreAndContrasena(String name, String contrasena);
    Optional<Usuario> findByNombre(String nombre);
    boolean existsByNombreAndContrasena(String nombre, String contrasena);
}