package com.administracion.dto.domicilios;

import java.io.Serializable;

import lombok.Data;

/**
 * DTO que contiene los atributos del vehiculo del DELIVERY
 */
@Data
public class DeliveryVehiculoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del vehiculo **/
	private Long id;

	/** Identificador del fabricante **/
	private Long idFabricante;

	/** Nombre del fabricante **/
	private String fabricante;

	/** Identificador del tipo de vehiculo **/
	private Long idTipoVehiculo;

	/** Nombre del tipo de vehiculo **/
	private String tipoVehiculo;

	/** Identificador del vehiculo **/
	private String placa;

	/** Cilindraje del vehiculo **/
	private String cilindraje;

	/** Indica si los datos del vehiculo fueron modificados **/
	private boolean datosVehiculoModificado;
}
