package com.administracion.dto.transversal;

import java.io.Serializable;

import lombok.Data;

/**
 * DTO que contiene los atributos del paginador, se debe utilizar este DTO para
 * las consultas masivas del sistema que se muestran en un p-table
 */
@Data
public class PaginadorDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** cantidad total de los registros */
	private Long cantidadTotal;

	/** es la cantidad de filas por paginas (0-10rowsPage, 10-10rowsPage, 20-10rowsPage)*/
	private String rowsPage;

	/** indica que cantidad va saltar en la consulta (skip0-10, skip10-10, skip20-10) */
	private String skip;
}
