package com.administracion.dto.configurar;

import java.io.Serializable;

import lombok.Data;

@Data
public class UsuariosDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long idUsuario;
	private String nombreUsuario;
	private String clave;
	private String idEstado;

}
