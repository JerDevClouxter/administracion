package com.administracion.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * DTO que se utiliza para configurar los atributos de IDIOMA
 */
@Data
public class IdiomaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** identificador del IDIOMA */
	private Long idIdioma;

	/** nombre del IDIOMA */
	private String nombre;

}
