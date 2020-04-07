package com.administracion.dto.autorizaciones;
import java.io.Serializable;

import lombok.Data;

/**
 * Clase que contiene los atributos generales de una Autorizacion
 */
@Data
public class AutorizacionDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Nombre de la loteria de la serie **/
	private String nombreLoteria;

	/** Fecha inicio de la serie **/
	private String fechaInicio;

	/** Fecha final de la serie **/
	private String fechaFinal;
}
