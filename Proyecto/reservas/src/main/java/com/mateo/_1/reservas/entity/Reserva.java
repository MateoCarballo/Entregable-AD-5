package com.mateo._1.reservas.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "reserva")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserva_id")
    private int reservaId;

    @Column(name = "usuario_id", nullable = false)
    private int usuarioId; // Enlace a usuario (solo guardamos id, por ahora)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habitacion_id", nullable = false)
    @JsonBackReference(value = "habitacion-reserva")
    private Habitacion habitacion;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(length = 20)
    private String estado; // Pendiente, Confirmada, Cancelada

   /*
    @Override
    public String toString() {
        return String.format("""
        Reserva:
        reservaId   -> %d
        usuarioId   -> %d
        habitacionId-> %s
        fechaInicio -> %s
        fechaFin    -> %s
        estado      -> %s
        """,
                reservaId,
                usuarioId,
                (habitacion != null ? habitacion.getHabitacionId() : "null"),
                fechaInicio != null ? fechaInicio.toString() : "null",
                fechaFin != null ? fechaFin.toString() : "null",
                estado != null ? estado : "null"
        );
    }
    */

    //TODO preguntar a jose. Si las variables de mi clase java
    // tienen un nombre difernente a los de mysql aqui que debo poner en la relacion
    // el nombre de la columna mysql o el de la variable de la clase que controla esta referencia
    // en este caso que pondria habiracion_id que es la tabla o habitacionId que es la variable java

}
