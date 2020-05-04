package com.administracion.dto.configurar;

import java.io.Serializable;

import lombok.Data;

@Data
public class TiposDocumentosDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long idTipoDocumento;
	private String descripcion;
	private String idEstado;
}
