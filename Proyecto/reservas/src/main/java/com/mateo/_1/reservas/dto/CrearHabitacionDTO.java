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
    private String usuario;
    private String contrasena;
    private int numeroHabitacion;
    private String tipo;
    private BigDecimal precio;
    private int idHotel;
}