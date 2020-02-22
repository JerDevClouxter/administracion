package com.administracion.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * DTO utilizado para configurar los atributos de Comisiones
 * @author juan
 *
 */
@Data
public class ComisionesDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** Identificador de Comisiones */
	private long idComision;
	
}
