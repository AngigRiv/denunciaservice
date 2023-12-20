package com.rivera.denunciaservice.dto;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class DenunciaDTO {
	private int id;
    private String dni;
    private LocalDate fecha;
    private String titulo;
    private String direccion;
    private String descripcion;
}
