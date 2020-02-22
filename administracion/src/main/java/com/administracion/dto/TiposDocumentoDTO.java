package com.administracion.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * DTO para encapsular la información de los tipos de documento
 * 
 * @author 
 *
 */
@Data
public class TiposDocumentoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** Identificador de TipoDocumento */
	private long idTipoDocumento;
	private String descripcion;
	private String estado;
}
