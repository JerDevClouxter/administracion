package com.administracion.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Data;

/**
 * Entidad que representa la tabla CUENTAS
 */
@Data
@Entity
@Table(name = "cuentas")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cuentas implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	private Long idCuenta;
	private Long idClaseCuenta;
	private String nombre;
}
