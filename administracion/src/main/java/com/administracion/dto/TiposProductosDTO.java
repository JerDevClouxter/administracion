package com.administracion.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * DTO utilizado para configurar los atributos de TiposProductos
 * @author juan
 *
 */
@Data
public class TiposProductosDTO implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/** Identificador de TipoProductos */
	private long idTipoProducto;

}
