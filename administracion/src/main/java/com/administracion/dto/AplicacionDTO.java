package com.administracion.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * DTO que se utiliza para configurar los atributos de APLICACION
 */
@Data
public class AplicacionDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** identificador de la APLICACION */
	private Long idAplicacion;

	/** nombre de la APLICACION */
	private String nombre;

	/** identificador del estado de la APLICACION */
	private String idEstado;

}
