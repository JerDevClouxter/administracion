package com.administracion.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Data;

/**
 * Entidad que representa la tabla EMPRESAS_PRODUCTOS
 * 
 * @author Jhonnatan Orozco Duque
 *
 */
@Data
@Entity
@Table(name = "EMPRESAS_PRODUCTOS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EmpresasProductos implements Serializable {
	/**
	 * Serial por defecto
	 */
	private static final long serialVersionUID = 1L;

	private Long idEmpresa;
	private Long idProducto;
	private Double valorMinimo;
	private Double valorMaximo;
	private Double valorMaximoDia;
	private String idEstado;
	private String horaInicioVenta;
	private String horaFinalVenta;

}
