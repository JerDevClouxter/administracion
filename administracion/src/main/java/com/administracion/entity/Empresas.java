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
 * Entidad que representa la tabla EMPRESAS
 */
@Data
@Entity
@Table(name = "EMPRESAS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Empresas implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_empresa")
	private Long idEmpresa;
	@Column(name = "id_empresa_padre")
	private Long idEmpresaPadre;
	@Column(name = "nit_empresa")
	private String nitEmpresa;
	@Column(name = "razon_social")
	private String razonSocial;
	@Column(name = "direccion")
	private String direccion;
	@Column(name = "telefono")
	private String telefono;
	@Column(name = "representante_legal")
	private String representanteLegal;

}
