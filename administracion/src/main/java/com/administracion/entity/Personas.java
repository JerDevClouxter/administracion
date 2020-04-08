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

@Data
@Entity
@Table(name = "PERSONAS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Personas implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_persona")
	private Long idPersona;
	@Column(name = "id_tipo_documento")
	private String idTipoDocumento;
	@Column(name = "numero_documento")
	private String numeroDocumento;
	@Column(name = "primer_nombre")
	private String primerNombre;
	@Column(name = "segundo_nombre")
	private String segundoNombre;
	@Column(name = "primer_apellido")
	private String primerApellido;
	@Column(name = "segundo_apellido")
	private String segundoApellido;
	@Column(name = "direccion")
	private String direccion;
	@Column(name = "telefono")
	private String telefono;
	@Column(name = "celular")
	private String celular;
	@Column(name = "correo_electronico")
	private String correoElectronico;
	@Column(name = "estrato")
	private Long estrato;

}
