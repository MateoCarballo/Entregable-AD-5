package com.mateo._1.usuarios.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "usuario")
@Builder

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private int id;

    @NonNull
    private String nombre;

    @NonNull
    private String correo_electronico;

    @NonNull
    private String direccion;

    @NonNull
    private String contrasena;
}
