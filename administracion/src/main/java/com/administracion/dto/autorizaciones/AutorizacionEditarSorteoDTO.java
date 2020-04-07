package com.administracion.dto.autorizaciones;

import java.util.Date;

import lombok.Data;

/**
 * Clase que contiene los atributos especificos para la autorizacion de edicion
 * de sorteos, este DTO se utiliza para convertirlo en JSON y viceversa
 */
@Data
public class AutorizacionEditarSorteoDTO extends AutorizacionDTO {
	private static final long serialVersionUID = 1L;

	/** Es la nueva fecha del sorteo **/
	private Date fechaSorteo;

	/** Es la nueva hora del sorteo **/
	private String horaSorteo;
}
