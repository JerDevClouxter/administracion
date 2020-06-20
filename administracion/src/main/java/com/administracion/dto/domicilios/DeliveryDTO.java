package com.administracion.dto.domicilios;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * DTO que contiene los atributos de un DELIVERY
 */
@Data
public class DeliveryDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del valor **/
	private Long id;

	/** Identificador del tipo de documento **/
	private String idTipoDocumento;

	/** Numero de documento **/
	private String nroDocumento;

	/** Nombre completo del delivery **/
	private String nombreCompleto;

	/** Primer Nombre del delivery **/
	private String primerNombre;

	/** Segundo Nombre del delivery **/
	private String segundoNombre;

	/** Primer Apellido del delivery **/
	private String primerApellido;

	/** Segundo Apellido del delivery **/
	private String segundoApellido;

	/** Correo electronico del delivery **/
	private String correo;

	/** Telefono del delivery **/
	private String telefono;

	/** Fecha de nacimiento del delivery **/
	private Date fechaNacimiento;

	/** Ciudad de nacimiento del delivery **/
	private Long ciudadNacimiento;

	/** Genero del delivery **/
	private String genero;

	/** Estado en la que se encuentra el delivery **/
	private String idEstado;

	/** Estado en la que se encuentra el delivery **/
	private boolean estado;

	/** Es el equipo asignado al delivery **/
	private DeliveryEquipoDTO equipoAsignado;

	/** Es el vehiculo del delivery **/
	private DeliveryVehiculoDTO vehiculo;
}
