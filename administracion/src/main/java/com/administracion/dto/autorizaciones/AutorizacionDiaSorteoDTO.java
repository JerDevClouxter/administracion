package com.administracion.dto.autorizaciones;

import java.io.Serializable;

import lombok.Data;

/**
 * Clase que contiene los atributos de un dia especifico para una autorizacion
 */
@Data
public class AutorizacionDiaSorteoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Es el nro del dia (Domingo=1, lunes=2, martes=3, miercoles=4 etc) */
	private Integer dia;

	/** Es el nombre del dia */
	private String nombreDia;

	/** Es la hora del dia en que se va realizar el sorteo */
	private String hora;

	/** Es la fecha especifica para el festivo */
	private String fecha;

	/** Es la descripcion del festivo */
	private String descripcionFestivo;

	/**
	 * Constructor de la clase donde se configura los valores globales
	 */
	public AutorizacionDiaSorteoDTO(Integer dia, String nombreDia, String hora) {
		this.dia = dia;
		this.nombreDia = nombreDia;
		this.hora = hora;
	}

	/**
	 * Constructor de la clase donde se configura los valores globales
	 */
	public AutorizacionDiaSorteoDTO(
			Integer dia, String nombreDia,
			String hora, String fecha,
			String descripcionFestivo) {
		this.dia = dia;
		this.nombreDia = nombreDia;
		this.hora = hora;
		this.fecha = fecha;
		this.descripcionFestivo = descripcionFestivo;
	}
}
