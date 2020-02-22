package com.administracion.dto;

import java.io.Serializable;

import lombok.Data;


/**
 * DTO utilizado para configurar los atributos de los Impuestos
 * @author juan
 */
@Data
public class ImpuestosDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** Identificador de Impuesto */
	private long idImpuesto;
	
	/** Nombre del Impuesto */ 
	private String nombre;
	
	/** Descripcion del Impuesto */
	private String descripcion;
	
}
