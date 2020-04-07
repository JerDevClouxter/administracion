package com.administracion.dto.autorizaciones;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Clase que contiene los atributos especificos para la autorizacion de
 * creacion o edicion de una serie, este DTO se utiliza para convertirlo
 * en JSON y viceversa
 */
@Data
public class AutorizacionCrearEditarSerieDTO extends AutorizacionDTO {
	private static final long serialVersionUID = 1L;

	/** Son los dias habiles que juega la serie **/
	private List<AutorizacionDiaSorteoDTO> diasHabilesQueJuegaSerie;

	/** Son los dias festivos que juega la serie **/
	private List<AutorizacionDiaSorteoDTO> diasFestivosQueJuegaSerie;

	/**
	 * Metodo que permite agregar un dia habil que juega la serie
	 */
	public void agregarDiaHabil(Integer dia, String hora) {
		if (this.diasHabilesQueJuegaSerie == null) {
			this.diasHabilesQueJuegaSerie = new ArrayList<>();
		}
		this.diasHabilesQueJuegaSerie.add(new AutorizacionDiaSorteoDTO(dia, hora));
	}

	/**
	 * Metodo que permite agregar un dia festivo que juega la serie
	 */
	public void agregarDiaFestivo(Integer dia, String hora, String fecha) {
		if (this.diasFestivosQueJuegaSerie == null) {
			this.diasFestivosQueJuegaSerie = new ArrayList<>();
		}
		this.diasFestivosQueJuegaSerie.add(new AutorizacionDiaSorteoDTO(dia, hora, fecha));
	}
}
