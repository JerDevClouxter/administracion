package com.administracion.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Data;

/**
 * Entidad que representa la tabla EMPRESAS_PRODUCTOS_COMISIONES
 *
 */
@Data
@Entity
@Table(name = "EMPRESAS_PRODUCTOS_COMISIONES")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EmpresaProductosComisiones implements Serializable {

	/**
	 * Serial version por defecto
	 */
	private static final long serialVersionUID = 1L;

	private Long idEmpresa;
	private Long idProducto;
	private Long idComision;
	private Double porcentajeComision;
	private Double valorFijoComision;
	private String idEstado;

}
