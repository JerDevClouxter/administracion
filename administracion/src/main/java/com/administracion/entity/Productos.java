package com.administracion.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Data;

/**
 * Entidad que representa la tabla PRODUCTOS
 * 
 * @author Jhonnatan Orozco Duque
 *
 */
@Data
@Entity
@Table(name = "PRODUCTOS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Productos implements Serializable {

	/**
	 * Serial version por defecto
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Long idProducto;
	private Long idTipoProducto;
	private String nombre;
	private String idStado;

}
