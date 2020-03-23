package com.administracion.dto.multinivel;

import java.io.Serializable;

import lombok.Data;

/**
 *  DTO utilizado para configurar los atributos de Comisiones
 */
@Data
public class ProductosDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idProducto;
	private Long idTipoProducto;
	private String nombre;
	private String idEstado;


}
