package com.administracion.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Data;

/**
 * Entidad que representa la tabla TipoDocumentos
 */
@Data
@Entity
@Table(name = "TipoDocumentos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TipoDocumentos implements Serializable{
	private static final long serialVersionUID = 1L;


}
