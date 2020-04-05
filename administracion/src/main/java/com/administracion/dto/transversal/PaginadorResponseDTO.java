package com.administracion.dto.transversal;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;

/**
 * DTO que contiene los datos del response para los paginadores de la aplicacion
 */
@Data
public class PaginadorResponseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** son los registros consultados */
	private ArrayList<Object> registros;

	/** cantidad total de los registros */
	private Long cantidadTotal;

	/**
	 * Metodo que permite agregar un registro al response del paginador
	 * @param registro agregar al response
	 */
	public void agregarRegistro(Object registro) {
		if (this.registros == null) {
			this.registros = new ArrayList<>();
		}
		this.registros.add(registro);
	}
}
