package com.administracion.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * DTO para encapsular la información de monedas
 * @author 
 *
 */
@Data
public class MonedasDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** Identificador de Moneda */
	private long idMoneda;
	
	/**Nombre de la moneda */
	private String nombre;
	
	/**Descripcion de la moneda */
	private String descripcion;
}
