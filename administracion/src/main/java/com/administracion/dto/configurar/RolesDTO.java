package com.administracion.dto.configurar;

import java.io.Serializable;

import lombok.Data;

@Data
public class RolesDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long idRol;
	private String nombre;
	private String descripcion;
	private String idEstado;

}
