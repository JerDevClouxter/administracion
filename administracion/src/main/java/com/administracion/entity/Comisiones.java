package com.administracion.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Data;

/**
 * Entidad que nos representa la tabla comisiones
 */
@Data
@Entity
@Table(name = "COMISIONES")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comisiones implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long idComision;
	private String nombre;
}
