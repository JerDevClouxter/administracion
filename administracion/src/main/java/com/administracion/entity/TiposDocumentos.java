package com.administracion.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Data;

/**
 * Entidad que representa la tabla TiposDocumentos
 */
@Data
@Entity
@Table(name = "TiposDocumentos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TiposDocumentos implements Serializable{
	private static final long serialVersionUID = 1L;


}
