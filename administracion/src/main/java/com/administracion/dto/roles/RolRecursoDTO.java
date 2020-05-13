package com.administracion.dto.roles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * DTO que contiene los atributos de un RECURSO asociado a un ROL
 */
@Data
public class RolRecursoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del RECURSO **/
	private Long id;

	/** Indica si el panel recurso esta cerrado **/
	private boolean panelRecursoCerrado;

	/** Indica si el recurso es valido */
	private boolean noValido;

	/** Indica si ya se dio submit para la creacion o edicion de roles */
	private boolean isSubmit;

	/** Son los ids de las acciones de este recurso **/
	private List<Long> idAcciones;

	/**
	 * Metodo que permite agrega un accion asociado a este recurso
	 */
	public void agregarAccion(Long idAccion) {
		if (this.idAcciones == null) {
			this.idAcciones = new ArrayList<>();
		}
		this.idAcciones.add(idAccion);
	}
}
