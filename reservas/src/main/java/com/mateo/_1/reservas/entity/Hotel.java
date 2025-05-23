package com.mateo._1.reservas.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "hotel")
@Data
@AllArgsConstructor //Genera un constructor con todos los parametros
@NoArgsConstructor //Genera un constructor sin parámetros (constructor vacío.)
@Builder

public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id")
    private int id;

    @Column(length = 100, nullable = false)
    private String nombre;

    // Por defecto ya son 255 @Column(length = 255)
    private String direccion;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "hotel-habitacion")
    private List<Habitacion> habitaciones;

    //Metodo para evitar la recursividad en las llamadas a los toString de cada entidad relacionada
    /*
    @Override
    public String toString() {
        StringBuilder habitacionesIds = new StringBuilder();
        if (habitaciones != null && !habitaciones.isEmpty()) {
            for (Habitacion h : habitaciones) {
                habitacionesIds.append(h.getHabitacionId()).append(", ");
            }
        } else {
            habitacionesIds.append("No hay habitaciones");
        }

        return String.format("""
        Hotel:
        id          -> %d
        nombre      -> %s
        direccion   -> %s
        total habitaciones -> %d
        habitaciones IDs   -> %s
        """,
                id,
                nombre,
                direccion,
                (habitaciones != null ? habitaciones.size() : 0),
                habitacionesIds.toString()
        );
    }
     */

}