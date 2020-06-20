package com.administracion.dto.domicilios;

import java.io.Serializable;

import lombok.Data;

/**
 * DTO que contiene los atributos del vehiculo del DELIVERY
 */
@Data
public class DeliveryVehiculoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del fabricante **/
	private Long idFabricante;

	/** Identificador del tipo de vehiculo **/
	private Long idTipoVehiculo;

	/** Identificador del vehiculo **/
	private String placa;

	/** Cilindraje del vehiculo **/
	private String cilindraje;
}
