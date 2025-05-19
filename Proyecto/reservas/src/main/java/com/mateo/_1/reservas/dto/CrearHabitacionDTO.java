package com.mateo._1.reservas.dto;

import com.mateo._1.reservas.enums.TipoHabitacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearHabitacionDTO {
    //(numeroHabitacion, tipo, precio y idHotel)
    private String nombreUsuario;
    private String contrasenaUsuario;
    private int numeroHabitacion;
    private TipoHabitacion tipo;
    private BigDecimal precio;
    private int idHotel;
}


