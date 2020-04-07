package com.administracion.dto.solicitudes;

import java.io.Serializable;

import lombok.Data;

/**
 * DTO que contiene los atributos para las solicitudes de calendario sorteo
 */
@Data
public class SolicitudCalendarioSorteoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador de la solicitud **/
	private Long idSolicitud;

	/** Fecha en la que solicitaron los cambios **/
	private String fechaSolicitud;

	/** Hora en la que solicitaron los cambios **/
	private String horaSolicitud;

	/** Tipo de solicitud (MODIFICACION, CREACION, ELIMINACION) **/
	private String tipoSolicitud;

	/** Nombre de la persona quien realiza la solicitud **/
	private String solicitante;

	/** Nombre de la loteria del sorteo **/
	private String loteria;

	/** Es el detalle de la solicitud calendario sorteo **/
	private DetalleSolicitudCalendarioSorteoDTO detalle;
}
