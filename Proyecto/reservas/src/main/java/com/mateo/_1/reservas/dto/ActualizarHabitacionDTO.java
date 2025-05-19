
package com.mateo._1.reservas.dto;

import com.mateo._1.reservas.enums.TipoHabitacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActualizarHabitacionDTO {
    private String nombreUsuario;
    private String contrasenaUsuario;
    private int id;
    private int numeroHabitacion;
    private TipoHabitacion tipo;
    private BigDecimal precio;
    private int idHotel;
    private boolean disponible;
}



