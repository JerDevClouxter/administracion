package com.administracion.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Data;

/**
 * Entidad que representa la tabla Monedas
 */
@Data
@Entity
@Table(name = "monedas")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Monedas implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	private Long idMoneda;
	private String nombre;
	private String descripcion;

}
