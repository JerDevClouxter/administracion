package com.administracion.dto.domicilios;

import java.io.Serializable;

import lombok.Data;

/**
 * DTO que contiene los atributos de un DELIVERY
 */
@Data
public class DeliveryDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del valor **/
	private Long id;

	/** Nombre del delivery **/
	private String nombre;

	/** Correo electronico del delivery **/
	private String correo;

	/** Telefono del delivery **/
	private String telefono;

	/** Estado en la que se encuentra el delivery **/
	private String estado;
}
