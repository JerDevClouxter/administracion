package com.administracion.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * DTO para encapsular la información de cuentas
 */
@Data
public class CuentasDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** identificador de la CUENTA */
	private Long idCuenta;
	
	/** identificador de la clase de la CUENTA */
	private Long idClaseCuenta;
	
	/** nombre de la CUENTA */
	private String nombre;

}
