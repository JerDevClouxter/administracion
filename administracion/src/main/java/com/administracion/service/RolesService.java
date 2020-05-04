package com.administracion.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.administracion.constant.Constants;
import com.administracion.constant.SQLConstant;
import com.administracion.constant.SQLTransversal;
import com.administracion.dto.roles.RolDTO;
import com.administracion.dto.solicitudes.FiltroBusquedaDTO;
import com.administracion.dto.transversal.PaginadorDTO;
import com.administracion.dto.transversal.PaginadorResponseDTO;
import com.administracion.enums.Numero;
import com.administracion.util.Util;

/**
 * Service que contiene los procesos de negocio para la administracion de los ROLES
 */
@Service
@SuppressWarnings("unchecked")
public class RolesService {

	/** Contexto de la persistencia del sistema */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Servicio que soporta el proceso de negocio para obtener los ROLES
	 * parametrizados en el sistema
	 *
	 * @param filtro, DTO que contiene los valores del filtro de busqueda
	 * @return DTO con la lista de roles parametrizados en el sistema
	 */
	public PaginadorResponseDTO getRoles(FiltroBusquedaDTO filtro) throws Exception {

		// se utiliza para encapsular la respuesta de esta peticion
		PaginadorResponseDTO response = new PaginadorResponseDTO();

		// se obtiene el from de la consulta
		StringBuilder from = new StringBuilder(SQLConstant.FROM_ROLES);

		// contiene los valores de los parametros del los filtros
		ArrayList<Object> parametros = new ArrayList<>();

		// se configura los filtros de busqueda ingresados
		setFiltrosBusquedaRoles(from, parametros, filtro);

		// se utiliza para obtener los datos del paginador
		PaginadorDTO paginador = filtro.getPaginador();

		// se valida si se debe contar el total de los registros
		response.setCantidadTotal(paginador.getCantidadTotal());
		if (response.getCantidadTotal() == null) {

			// se configura el query con los valores de los filtros
			Query qcount = this.em.createNativeQuery(SQLTransversal.getSQLCount(from.toString()));
			Util.setParameters(qcount, parametros);

			// se configura la cantidad total de acuerdo al filtro de busqueda
			response.setCantidadTotal(((BigInteger) qcount.getSingleResult()).longValue());
		}

		// solo se consultan los registros solo si existen de acuerdo al filtro
		if (response.getCantidadTotal() != null &&
			!response.getCantidadTotal().equals(Numero.ZERO.valueL)) {

			// se configura el SQL de la consulta principal
			StringBuilder sql = new StringBuilder(SQLConstant.SELECT_ROLES + from.toString());

			// se ordena la consulta
			sql.append(SQLConstant.ORDER_GROUP_ROLES);

			// se configura la paginacion de la consulta
			SQLTransversal.getSQLPaginator(paginador.getSkip(), paginador.getRowsPage(), sql);

			// se configura el query con los valores de los filtros y se obtiene los datos
			Query query = this.em.createNativeQuery(sql.toString());
			Util.setParameters(query, parametros);
			List<Object[]> result = query.getResultList();

			// se recorre cada role
			RolDTO rol;
			String split;
			for (Object[] roles : result) {
				rol = new RolDTO();
				rol.setId(Long.valueOf(Util.getValue(roles, Numero.ZERO.valueI)));
				rol.setNombre(Util.getValue(roles, Numero.UNO.valueI));
				rol.setDescripcion(Util.getValue(roles, Numero.DOS.valueI));
				split = Util.getValue(roles, Numero.TRES.valueI);
				if (!Util.isNull(split)) {
					rol.setEmpresas(split.split(Constants.COMA));
				}
				split = Util.getValue(roles, Numero.CUATRO.valueI);
				if (!Util.isNull(split)) {
					rol.setRecursos(split.split(Constants.COMA));
				}
				response.agregarRegistro(rol);
			}
		}
		return response;
	}

	/**
	 * Metodo que permite configurar los filtros de busqueda para
	 * obtener los ROLES parametrizados en el sistema
	 */
	private void setFiltrosBusquedaRoles(
			StringBuilder from,
			ArrayList<Object> parametros,
			FiltroBusquedaDTO filtro) {

		// filtro por el identificador de la empresa asociada al ROLE
		Long idEmpresa = filtro.getIdEmpresa();
		if (idEmpresa != null && !idEmpresa.equals(Numero.ZERO.valueL)) {
			from.append(" WHERE RE.ID_EMPRESA=?");
			parametros.add(idEmpresa);
		}

		// filtro por el estado del ROLE
		String estado = filtro.getEstado();
		if (!Util.isNull(estado)) {
			from.append(from.length() > Numero.ZERO.valueI.intValue() ? " AND " : " WHERE ");
			from.append("R.ID_ESTADO=?");
			parametros.add(estado);
		}

		// filtro por nombre del ROLE
		String nombre = filtro.getNombre();
		if (!Util.isNull(nombre)) {
			from.append(from.length() > Numero.ZERO.valueI.intValue() ? " AND " : " WHERE ");
			from.append("UPPER(R.NOMBRE)=UPPER(?)");
			parametros.add(nombre);
		}
	}
}
