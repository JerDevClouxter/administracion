package com.administracion.dto.configurar;

import java.io.Serializable;

import lombok.Data;

@Data
public class PersonasDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long idPersona;
	private String idTipoDocumento;
	private String numeroDocumento;

}
