package com.administracion.dto;

import java.io.Serializable;

import lombok.Data;

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
