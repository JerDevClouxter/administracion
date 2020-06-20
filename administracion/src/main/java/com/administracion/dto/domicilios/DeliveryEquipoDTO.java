package com.administracion.dto.domicilios;

import java.io.Serializable;

import lombok.Data;

/**
 * DTO que contiene los atributos del equipo asignado a un DELIVERY
 */
@Data
public class DeliveryEquipoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del fabricante **/
	private Long idFabricante;

	/** Modelo del equipo **/
	private String modelo;

	/** Nro sim del equipo **/
	private String nroSim;

	/** Nro iemi del equipo **/
	private String nroImei;
}
