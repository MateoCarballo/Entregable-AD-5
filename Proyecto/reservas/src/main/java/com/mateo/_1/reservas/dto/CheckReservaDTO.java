package com.mateo._1.reservas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckReservaDTO {
    private int idUsuario;
    private int idHotel;
    private int idReserva;
}
