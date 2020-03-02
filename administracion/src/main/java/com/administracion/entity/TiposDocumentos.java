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
 * Entidad que representa la tabla TiposDocumentos
 */
@Data
@Entity
@Table(name = "TIPOS_DOCUMENTOS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TiposDocumentos implements Serializable{
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tipo_documento")
	private Long idTipoIdentificacion;
}
