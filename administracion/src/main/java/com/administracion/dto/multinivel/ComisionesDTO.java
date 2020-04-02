package com.administracion.dto.multinivel;

import java.io.Serializable;

import lombok.Data;

/**
 * DTO utilizado para configurar los atributos de Comisiones
 */
@Data
public class ComisionesDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** Identificador de Comisiones */
	private long idComision;
	private String nombre;
	
}
