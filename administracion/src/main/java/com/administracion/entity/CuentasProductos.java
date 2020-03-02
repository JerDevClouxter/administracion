package com.administracion.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Data;

/**
 * Entidad que representa la tabla CUENTAS_PRODUCTOS
 *
 */
@Data
@Entity
@Table(name = "CUENTAS_PRODUCTOS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CuentasProductos implements Serializable {
	
	/**
	 * Serial version por defecto
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Long idEmpresa;
	private Long idProducto;
	private Long idCuenta;
	private String codCuenta;
	

}
