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

	/** Identificador del tipo de solicitud **/
	private Integer idTipoSolicitud;

	/** Motivo de la solicitud **/
	private String motivoSolicitud;

	/** Identificador de la serie o del detalle sorteo **/
	private Long idSerieDetalle;

	/** Identifica si el idSerieDetalle es para el campo ID_SORTEO o ID_SORTEO_DETALLE **/
	private String campo;

	/** Contiene los datos original de la serie o el detalle**/
	private AutorizacionDTO antes;

	/** Contiene los datos a modificar, cancelar o crear del sorteo**/
	private AutorizacionDTO despues;
}
