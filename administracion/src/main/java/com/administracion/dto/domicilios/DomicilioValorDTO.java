package com.administracion.dto.domicilios;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * DTO que contiene los atributos de un VALOR para domicilio
 */
@Data
public class DomicilioValorDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del valor **/
	private Long id;

	/** Nombre del departamento **/
	private String departamento;

	/** Identificador del departamento **/
	private Long idDepartamento;

	/** Nombre de la ciudad **/
	private String ciudad;

	/** Identificador de la ciudad **/
	private Long idCiudad;

	/** Nombre de la localidad **/
	private String localidad;

	/** Identificador de la localidad **/
	private Long idLocalidad;

	/** Zona del valor del domicilio (sur, norte) **/
	private String zona;

	/** Valor del domicilio **/
	private BigDecimal valor;

	/** Estado en la que se encuentra el valor **/
	private String estado;
}
