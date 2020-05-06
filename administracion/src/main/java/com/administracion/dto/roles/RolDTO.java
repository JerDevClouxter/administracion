package com.administracion.dto.roles;

import java.io.Serializable;

import lombok.Data;

/**
 * DTO que contiene los atributos de un ROLE
 */
@Data
public class RolDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del ROL **/
	private Long id;

	/** Nombre del ROL **/
	private String nombre;

	/** Descripcion del ROL **/
	private String descripcion;

	/** Son los nombres de las empresas asociadas al ROL, separadas por coma**/
	private String empresas;

	/** Son los nombres de los recursos asociados al ROL, separadas por coma **/
	private String recursos;
}
