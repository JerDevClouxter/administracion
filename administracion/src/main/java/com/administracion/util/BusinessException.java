package com.administracion.util;

/**
 * Clase que identifica el tipo de exception de negocio
 */
public class BusinessException extends Exception {

	/**
	 * Serial por defecto
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la Exception
	 * 
	 * @param msj, es el mensaje de la excepción ocurrido
	 */
	public BusinessException(String msj) {
		super(msj);
	}
}
