package com.administracion.dto;

import java.io.Serializable;

public class TipoDocumentosDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** Identificador de TipoDocumento */
	private long idTipoDocumento;
	private String descripcion;
	private String estado;
}
