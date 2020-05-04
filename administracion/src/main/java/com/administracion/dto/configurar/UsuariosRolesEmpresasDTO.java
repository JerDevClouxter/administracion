package com.administracion.dto.configurar;

import java.io.Serializable;

import lombok.Data;

@Data
public class UsuariosRolesEmpresasDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long idUsuario;
	private Long idRol;
	private Long idEmpresa;
	private String idEstado;

}
