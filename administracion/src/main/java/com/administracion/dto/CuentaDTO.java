package com.administracion.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * DTO que se utiliza para configurar los atributos de la CUENTA
 */
@Data
public class CuentaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** identificador de la CUENTA */
	private Long idCuenta;
	
	/** identificador de la clase de la CUENTA */
	private Long idClaseCuenta;
	
	/** nombre de la CUENTA */
	private String nombre;

}
