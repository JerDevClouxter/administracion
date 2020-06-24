package com.administracion.dto.solicitudes;

import java.io.Serializable;
import java.util.Date;

import com.administracion.dto.transversal.PaginadorDTO;

import lombok.Data;

/**
 * DTO que contiene los atributos para los filtros de busqueda de las
 * solicitudes realizadas en el sistema
 */
@Data
public class FiltroBusquedaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Paginador para obtener los resultados de la busqueda **/
	private PaginadorDTO paginador;

	/** Fecha de inicio de la solicitud **/
	private Date fechaInicio;

	/** Fecha de final de la solicitud **/
	private Date fechaFinal;

	/** filtro por identificador de la empresa **/
	private Long idEmpresa;

	/** filtro por estado, tipo Boolean dado que puede llegar 3 valores **/
	private Boolean estado;

	/** filtro por nombre **/
	private String nombre;

	/** filtro por identificador del departamento **/
	private Long idDepartamento;

	/** filtro por identificador de la ciudad **/
	private Long idCiudad;
	
	/** filtro por tipo de documento **/
	private String tipoDocumento;
	
	/** filtro por numero de documentp **/
	private String numeroDocumento;
	
	
}
