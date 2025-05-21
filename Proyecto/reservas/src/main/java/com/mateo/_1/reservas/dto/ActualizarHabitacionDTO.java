
package com.mateo._1.reservas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActualizarHabitacionDTO {
    private String usuario;
    private String contrasena;
    private int id;
    private int numeroHabitacion;
    private String tipo;
    private BigDecimal precio;
    private int idHotel;
    private boolean disponible;
}



