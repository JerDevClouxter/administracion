package com.administracion.dto.domicilios;

import java.io.Serializable;

import lombok.Data;

/**
 * DTO que contiene los atributos del equipo asignado a un DELIVERY
 */
@Data
public class DeliveryEquipoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del equipo **/
	private Long id;

	/** Identificador del fabricante **/
	private Long idFabricante;

	/** Nombre del fabricante **/
	private String fabricante;

	/** Modelo del equipo **/
	private String modelo;

	/** Nro sim del equipo **/
	private String nroSim;

	/** Nro iemi del equipo **/
	private String nroImei;

	/** Indica si los datos del equipo fueron modificados **/
	private boolean datosEquipoModificado;
}
