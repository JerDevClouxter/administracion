package com.administracion.dto.autorizaciones;

import com.administracion.enums.EstadoEnum;

import lombok.Data;

/**
 * Clase que contiene los atributos especificos para la autorizacion de
 * cancelacion de una serie o sorteo, este DTO se utiliza para convertirlo en
 * JSON y viceversa
 */
@Data
public class AutorizacionCancelarSerieSorteoDTO extends AutorizacionDTO {
	private static final long serialVersionUID = 1L;

	/** Estado de la serie o sorteo a cancelar **/
	private String estado = EstadoEnum.CANCELADO.name();

	/** Solo aplica cuando cancelan un sorteo y no toda la serie **/
	private String fechaSorteoCancelar;
}
