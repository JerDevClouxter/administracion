package com.administracion.dto.configurar;

import java.io.Serializable;

import lombok.Data;

@Data
public class EmpresasDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long idEmpresa;
	private Long idEmpresaPadre;
	private String nitEmpresa;
	private String razonSocial;
	private String direccion;
	private String telefono;
	private String representanteLegal;

}
