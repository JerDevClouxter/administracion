package com.administracion.dto.multinivel;

import java.io.Serializable;

import lombok.Data;

/**
 * DTO que representa la información de las comisiones
 * asociadas a los productos de una empresa
 *
 */
@Data
public class EmpresasProductosComisionesDTO  implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long idEmpresa;
	private Long idProducto;
	private Long idComision;
	private Double porcentajeComision;
	private Double valorFijoComision;
	private String idEstado;
	private String nombreProducto;
	private Boolean esPrimerVez;
	private Double porcentajeComisionPadre;
	private Double valorFijoComisionPadre;
	
}
