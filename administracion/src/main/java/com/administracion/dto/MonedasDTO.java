package com.administracion.dto;

import java.io.Serializable;

public class MonedasDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** Identificador de Moneda */
	private long idMoneda;
	
	/**Nombre de la moneda */
	private String nombre;
	
	/**Descripcion de la moneda */
	private String descripcion;
}
