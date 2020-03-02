package com.administracion.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Data;

/**
 * Entidad que representa la tabla EMPRESAS_PRODUCTOS
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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_empresa")
	private Long idEmpresa;
	@Column(name = "id_producto")
	private Long idProducto;
	@Column(name = "valor_minimo")
	private Double valorMinimo;
	@Column(name = "valor_maximo")
	private Double valorMaximo;
	@Column(name = "valor_maximo_dia")
	private Double valorMaximoDia;
	@Column(name = "id_estado")
	private String idEstado;
	@Column(name = "hora_inicio_venta")
	private String horaInicioVenta;
	@Column(name = "hora_final_venta")
	private String horaFinalVenta;

}
