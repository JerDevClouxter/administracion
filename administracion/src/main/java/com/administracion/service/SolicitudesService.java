package com.administracion.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.administracion.constant.SQLConstant;
import com.administracion.constant.SQLTransversal;
import com.administracion.dto.solicitudes.FiltroBusquedaDTO;
import com.administracion.dto.solicitudes.SolicitudCalendarioSorteoDTO;
import com.administracion.dto.transversal.PaginadorDTO;
import com.administracion.dto.transversal.PaginadorResponseDTO;
import com.administracion.enums.Numero;
import com.administracion.util.Util;

/**
 * Service que contiene los procesos de negocio para la administracion de las solicitudes
 */
@Service
@SuppressWarnings("unchecked")
public class SolicitudesService {

	/** Contexto de la persistencia del sistema */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Servicio que soporta el proceso de negocio para obtener las solicitudes
	 * pendientes para los calendarios de los sorteos
	 *
	 * @param filtro, DTO que contiene los valores del filtro de busqueda
	 * @return DTO con la lista de solicitudes para los calendarios de los sorteos
	 */
	public PaginadorResponseDTO getSolicitudesCalendarioSorteos(FiltroBusquedaDTO filtro) throws Exception {

		// se utiliza para encapsular la respuesta de esta peticion
		PaginadorResponseDTO response = new PaginadorResponseDTO();

		// se obtiene el from de la consulta
		StringBuilder from = new StringBuilder(SQLConstant.FROM_SOLICITUDES_CALENDARIO_SORTEO);

		// contiene los valores de los parametros del los filtros
		ArrayList<Object> parametros = new ArrayList<>();

		// se configura los filtros de busqueda ingresados
		setFiltrosBusquedaCalendarioSorteos(from, parametros, filtro);

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
			StringBuilder sql = new StringBuilder(SQLConstant.SELECT_SOLICITUDES_CALENDARIO_SORTEO + from.toString());

			// se ordena la consulta
			sql.append(SQLConstant.ORDERBY_SOLICITUDES_CALENDARIO_SORTEO);

			// se configura la paginacion de la consulta
			SQLTransversal.getSQLPaginator(paginador.getSkip(), paginador.getRowsPage(), sql);

			// se configura el query con los valores de los filtros y se obtiene los datos
			Query query = this.em.createNativeQuery(sql.toString());
			Util.setParameters(query, parametros);
			List<Object[]> result = query.getResultList();

			// se recorre cada solicitud
			SolicitudCalendarioSorteoDTO solicitud;
			for (Object[] programacion : result) {
				solicitud = new SolicitudCalendarioSorteoDTO();
				solicitud.setIdSolicitud(Long.valueOf(Util.getValue(programacion, Numero.ZERO.valueI)));
				solicitud.setFechaSolicitud(Util.getValue(programacion, Numero.UNO.valueI));
				solicitud.setHoraSolicitud(Util.getValue(programacion, Numero.DOS.valueI));
				solicitud.setTipoSolicitud(Util.getValue(programacion, Numero.TRES.valueI));
				solicitud.setSolicitante(Util.getValue(programacion, Numero.CUATRO.valueI));
				solicitud.setLoteria(Util.getValue(programacion, Numero.CINCO.valueI));
				solicitud.setFechaSorteoSerie(Util.getValue(programacion, Numero.SEIS.valueI));
				response.agregarRegistro(solicitud);
			}
		}
		return response;
	}

	/**
	 * Metodo que permite configurar los filtros de busqueda para
	 * las solicitudes de los calendarios de los sorteos
	 *
	 * @param from, es el SQL del FROM para obtener las solicitudes
	 * @param parametros, instancia para los valores de los filtros
	 * @param filtro, contiene los datos de los filtros de busqueda
	 */
	private void setFiltrosBusquedaCalendarioSorteos(
			StringBuilder from,
			ArrayList<Object> parametros,
			FiltroBusquedaDTO filtro) {

		// filtro por fecha de inicio
		Date fechaInicio = filtro.getFechaInicio();
		if (fechaInicio != null) {
			from.append(SQLConstant.FILTER_SOLICITUDES_CALENDARIO_FECHA_INICIO);
			parametros.add(Util.removeTime(fechaInicio));
		}

		// filtro por fecha final
		Date fechaFinal = filtro.getFechaFinal();
		if (fechaFinal != null) {
			from.append(SQLConstant.FILTER_SOLICITUDES_CALENDARIO_FECHA_FINAL);
			parametros.add(Util.removeTime(fechaFinal));
		}
	}
}
