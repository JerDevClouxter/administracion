package com.administracion.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Data;

@Data
@Entity
@Table(name = "USUARIOS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Usuarios implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_usuario")
	private Long idUsuario;
	@Column(name = "nombre_usuario")
	private String nombreUsuario;
	@Column(name = "clave")
	private String clave;
	@Column(name = "id_estado")
	private String idEstado;
	@Column(name = "primer_ingreso")
	private Long primerIngreso;

}
