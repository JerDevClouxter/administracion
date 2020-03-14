package com.administracion.dto.multinivel;

import java.io.Serializable;

import lombok.Data;
/**
 * DTO que representa la información de las cuentas
 * asociadas a los productos de una empresa
 *
 */
@Data
public class CuentasProductosDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long idEmpresa;
	private Long idProducto;
	private Long idCuenta;
	private String codCuenta;
	private String nombreCuenta;
	private Integer cuentaAsociada;
	private String nombreProducto;
	
}
