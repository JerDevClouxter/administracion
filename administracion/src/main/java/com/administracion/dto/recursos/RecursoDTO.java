package com.administracion.dto.recursos;

import java.io.Serializable;

import lombok.Data;

/**
 * DTO que contiene los atributos de un RECURSO
 */
@Data
public class RecursoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del RECURSO **/
	private Long id;

	/** Nombre del RECURSO **/
	private String nombre;

	/** Descripcion del RECURSO **/
	private String descripcion;

	/** URL donde se aloja la pagina del RECURSO **/
	private String url;

	/** ICONO representativo del RECURSO que se muestra en el MENU **/
	private String icono;

	/** Identificador de la aplicacion que contiene este RECURSO **/
	private Integer idAplicacion;

	/** Si este recurso es un sub-item de otro recurso, este es el id padre **/
	private Long idRecursoPadre;

	/** Identificador del estado del RECURSO **/
	private String idEstado;

	/** Nombre de la aplicacion que contiene este RECURSO **/
	private String nombreAplicacion;

	/** Si este recurso es un sub-item de otro recurso, este es el nombre padre **/
	private String nombreRecursoPadre;

	/** Se utiliza para la creacion o edicion del recurso */
	private boolean estado;
}
