package com.mateo._1.reservas.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/*
TODO preguntar a Jose.
Tiene sentido que pongamos dentro de la clase que se vincula a tabla todos los objetos a no nulo
para evitarlos en mysql y si necesitamos partes usar DTO ?
 */
@Entity
@Table(name = "habitacion")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "habitacion_id")
    private int habitacionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonBackReference(value = "hotel-habitacion")
    private Hotel hotel;

    @Column(name = "numero_habitacion", nullable = false)
    private int numeroHabitacion;

    @Column(length = 50)
    private String tipo; // Individual, Doble, Triple, Suite

    @Column(precision = 10, scale = 2)
    private BigDecimal precio;

    private Boolean disponible;

    @OneToMany(mappedBy = "habitacion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "habitacion-reserva")
    private List<Reserva> reservas;
    /*
    @Override
    public String toString() {
        StringBuilder reservasIds = new StringBuilder();

        if (reservas != null && !reservas.isEmpty()) {
            for (Reserva r : reservas) {
                reservasIds.append(r.getReservaId()).append(", ");
            }
            reservasIds.setLength(reservasIds.length() - 2); // quitar última coma y espacio
        } else {
            reservasIds.append("No hay reservas");
        }

        return String.format("""
        Habitacion:
        habitacionId    -> %d
        hotelId         -> %s
        numeroHabitacion-> %d
        tipo            -> %s
        precio          -> %s
        disponible      -> %s
        total reservas  -> %d
        reservas IDs    -> %s
        """,
                habitacionId,
                (hotel != null ? hotel.getId() : "null"),
                numeroHabitacion,
                tipo,
                precio != null ? precio.toString() : "null",
                disponible != null ? disponible.toString() : "null",
                reservas != null ? reservas.size() : 0,
                reservasIds.toString()
        );
     */
    }

    /*
    BUSCABA USAR UN ENUMERADO BASANDOME EN LO QUE SE VE EN EL SCRIPT DE LA DATABASE PARA SER MAS ESTRICTO

    private TipoHabitacion tipo;

    @Enumerated(EnumType.STRING)
    @NonNull
    @Column(length = 50, nullable = false)
    @Column(length = 50)
    public enum TipoHabitacion{
        INDIVIDUAL, DOBLE, TRIPLE, SUITE
    }
     */

    /**
     * ¿Por qué BigDecimal?
     * Precisión exacta: Evita problemas de redondeo con números decimales
     *
     * Control preciso: Ideal para operaciones monetarias
     *
     * Soporte nativo: JPA/JPA lo maneja perfectamente con anotaciones
     */

