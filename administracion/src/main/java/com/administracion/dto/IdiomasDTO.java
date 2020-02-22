package com.administracion.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * DTO para encapsular la información de idiomas
 */
@Data
public class IdiomasDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** identificador del IDIOMA */
	private Long idIdioma;

	/** nombre del IDIOMA */
	private String nombre;

}
