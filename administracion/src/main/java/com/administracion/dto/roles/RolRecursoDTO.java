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
