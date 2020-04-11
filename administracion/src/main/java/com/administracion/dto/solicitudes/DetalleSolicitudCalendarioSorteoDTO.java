package com.administracion.dto.solicitudes;

import java.io.Serializable;

import com.administracion.dto.autorizaciones.AutorizacionDTO;

import lombok.Data;

/**
 * DTO que contiene los datos del detalle de la solicitud calendario sorteo
 */
@Data
public class DetalleSolicitudCalendarioSorteoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del usuario quien autoriza la solicitud **/
	private Long usuarioAutoriza;

	/** Identificador de la solicitud **/
	private Long idSolicitud;

	/** Motivo de la solicitud **/
	private String motivoSolicitud;

	/** Identificador de la serie o del detalle sorteo **/
	private Long idSerieDetalle;

	/** Identifica si la solicitud es para toda la serie o solamente para un sorteo **/
	private boolean esSolicitudTodaLaSerie;

	/** Identifica si la solicitud es para CREACION **/
	private boolean esSolicitudCreacion;

	/** Identifica si la solicitud es para MODIFICACION **/
	private boolean esSolicitudModificacion;

	/** Identifica si la solicitud es para CANCELACION **/
	private boolean esSolicitudCancelacion;

	/** Contiene los datos original de la serie o el detalle **/
	private AutorizacionDTO antes;

	/** Contiene los datos a modificar, cancelar o crear del sorteo **/
	private AutorizacionDTO despues;
}
