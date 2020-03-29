package com.administracion.dto.multinivel;

import java.io.Serializable;

import lombok.Data;

/**
 * DTO que representa la información de los productos
 * asociadas a una empresa
 *
 */
@Data
public class EmpresasProductosDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long idEmpresa;
	private Long idProducto;
	private Double valorMinimo;
	private Double valorMaximo;
	private Double valorMaximoDia;
	private String horaInicioVenta;
	private String horaFinalVenta;
	private String nombreProducto;
	private Integer idEstado;
	private Boolean esPrimerVez;
}
