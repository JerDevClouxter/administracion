package com.administracion.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * DTO para encapsular la informacion de empresas
 */
@Data
public class EmpresasDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** identificador de la EMPRESA */
	private Long idEmpresa;
	
	/** numero NIT de la EMPRESA */
	private String nitEmpresa;
	
	/** razon social de la EMPRESA */
	private String razonSocial;
	
	/** direccion de la EMPRESA */
	private String direccion;
	
	/** telefono de la EMPRESA */
	private String telefono;
	
	/** representante legal de la EMPRESA */
	private String representanteLegal;

}
