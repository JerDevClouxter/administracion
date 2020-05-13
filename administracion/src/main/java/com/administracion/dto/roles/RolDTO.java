package com.administracion.dto.roles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.administracion.util.Util;

import lombok.Data;

/**
 * DTO que contiene los atributos de un ROLE
 */
@Data
public class RolDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Identificador del ROL **/
	private Long id;

	/** Nombre del ROL **/
	private String nombre;

	/** Descripcion del ROL **/
	private String descripcion;

	/** Es el nombre del estado del ROL **/
	private String nombreEstado;

	/** Se utiliza para la creacion o edicion del ROL. true=activo, false=inactivo */
	private boolean estado;

	/** Son los nombres de las empresas asociadas al ROL, separadas por coma **/
	private String verEmpresas;

	/** Son los nombres de los recursos asociados al ROL, separadas por coma **/
	private String verRecursos;

	/** Son los ids de las empresas para la edicion o creacion de este ROL **/
	private List<Long> empresas;

	/** Son las empresas para la edicion o creacion de este ROL **/
	private List<RolRecursoDTO> recursos;

	/** Indica si la informacion general fue modificado **/
	private boolean infoGeneralModificado;

	/** Indica si los recursos fueron modificados **/
	private boolean recursosModificado;

	/** Indica si las companias fueron modificados **/
	private boolean companiasModificado;

	/**
	 * Metodo que permite agrega una empresa asociada a este ROL
	 */
	public void agregarEmpresa(String idEmpresa) {
		if (this.empresas == null) {
			this.empresas = new ArrayList<>();
		}
		this.empresas.add(Long.valueOf(idEmpresa));
	}

	/**
	 * Metodo que permite agregar un recurso asociado a este ROL
	 */
	public void agregarRecurso(String idRecurso, String idAccion) {

		// debe existir los ids de recurso y accion
		if (!Util.isNull(idRecurso) && !Util.isNull(idAccion)) {

			// se hace la conversion de string to Long
			Long idRecursoL = Long.valueOf(idRecurso);
			Long idAccionL = Long.valueOf(idAccion);

			// se verifica si este es el primer recurso para este ROL
			if (this.recursos == null) {
				this.recursos = new ArrayList<>();
				addRecurso(idRecursoL, idAccionL);
			} else {

				// si no es el primer recurso se procede a buscarlo
				RolRecursoDTO recursoData = null;
				for (RolRecursoDTO recurso : this.recursos) {
					if (recurso.getId().equals(idRecursoL)) {
						recursoData = recurso;
						recursoData.agregarAccion(idAccionL);
						break;
					}
				}

				// si no existe el recurso se procede agregarlo
				if (recursoData == null) {
					addRecurso(idRecursoL, idAccionL);
				}
			}
		}
	}

	/**
	 * Metodo que permite agreagar un recurso a la lista de recursos
	 */
	private void addRecurso(Long idRecurso, Long idAccion) {
		RolRecursoDTO recurso = new RolRecursoDTO();
		recurso.setPanelRecursoCerrado(true);
		recurso.setId(idRecurso);
		recurso.agregarAccion(idAccion);
		this.recursos.add(recurso);
	}
}
