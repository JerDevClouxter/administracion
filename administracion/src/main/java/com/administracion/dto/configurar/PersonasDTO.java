package com.administracion.dto.configurar;

import java.io.Serializable;

import lombok.Data;

@Data
public class PersonasDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long idPersona;
	private String idTipoDocumento;
	private String numeroDocumento;
	private String primerNombre;
	private String segundoNombre;
	private String primerApellido;
	private String segundoApellido;
	private String direccion;
	private String telefono;
	private String celular;
	private String correoElectronico;
	private Long estrato;
	

}
