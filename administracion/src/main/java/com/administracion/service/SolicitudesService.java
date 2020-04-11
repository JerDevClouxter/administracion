package com.administracion.service;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.administracion.constant.Constants;
import com.administracion.constant.MessagesBussinesKey;
import com.administracion.constant.SQLConstant;
import com.administracion.constant.SQLTransversal;
import com.administracion.constant.TiposAutorizacionesConstant;
import com.administracion.dto.autorizaciones.AutorizacionCancelarSerieSorteoDTO;
import com.administracion.dto.autorizaciones.AutorizacionCrearEditarSerieDTO;
import com.administracion.dto.autorizaciones.AutorizacionEditarSorteoDTO;
import com.administracion.dto.solicitudes.DetalleSolicitudCalendarioSorteoDTO;
import com.administracion.dto.solicitudes.FiltroBusquedaDTO;
import com.administracion.dto.solicitudes.SolicitudCalendarioSorteoDTO;
import com.administracion.dto.transversal.PaginadorDTO;
import com.administracion.dto.transversal.PaginadorResponseDTO;
import com.administracion.enums.EstadoEnum;
import com.administracion.enums.Numero;
import com.administracion.jdbc.UtilJDBC;
import com.administracion.jdbc.ValueSQL;
import com.administracion.util.BusinessException;
import com.administracion.util.Util;
import com.google.gson.Gson;

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
				response.agregarRegistro(solicitud);
			}
		}
		return response;
	}

	/**
	 * Servicio que permite consultar el detalle de la solicitud para calendario sorteos
	 *
	 * @param solicitud, DTO que contiene los datos de la solicitud
	 * @return DTO con los datos del detalle de la solicitud para calendario sorteos
	 */
	public DetalleSolicitudCalendarioSorteoDTO getDetalleSolicitudCalendarioSorteos(
			SolicitudCalendarioSorteoDTO solicitud) throws Exception {

		// se utiliza para encapsular la respuesta de esta peticion
		DetalleSolicitudCalendarioSorteoDTO response = new DetalleSolicitudCalendarioSorteoDTO();

		// los datos de la solicitud es requerido
		if (solicitud != null) {

			// se necesita el identificador de la solicitud para consultar sus detalles
			Long idSolicitud = solicitud.getIdSolicitud();
			if (idSolicitud != null) {
				response.setIdSolicitud(idSolicitud);

				// se consulta los detalles de esta solicitud
				Query q = this.em.createNativeQuery(SQLConstant.SELECT_DETALLE_SOLICITUD_CALENDARIO_SORTEO);
				q.setParameter(Numero.UNO.valueI, idSolicitud);
				List<Object[]> detalles = q.getResultList();

				// se valida si esta solicitud tiene detalles
				if (detalles != null && !detalles.isEmpty()) {

					// se recorre cada detalle de esta solicitud
					String campo;
					String json = null;
					String horaSorteo = null;
					Timestamp fechaSorteo = null;
					Integer idTipoSolicitud = null;
					for (Object[] detalle : detalles) {

						// se obtiene el tipo de solicitud y el motivo solamente del primer registro
						if (idTipoSolicitud == null) {
							idTipoSolicitud = Integer.valueOf(Util.getValue(detalle, Numero.ZERO.valueI));
							response.setEsSolicitudCreacion(TiposAutorizacionesConstant.CREACION_SORTEOS.equals(idTipoSolicitud));
							response.setEsSolicitudModificacion(TiposAutorizacionesConstant.MODIFICAR_SORTEOS.equals(idTipoSolicitud));
							response.setEsSolicitudCancelacion(TiposAutorizacionesConstant.CANCELAR_SORTEOS.equals(idTipoSolicitud));
							response.setMotivoSolicitud(Util.getValue(detalle, Numero.UNO.valueI));
						}

						// se obtiene el valor del campo(AUTORIZACIONES_DETALLES.CAMPO)
						campo = Util.getValue(detalle, Numero.DOS.valueI);

						// se verifica a que campo se va hacer la transaccion de esta solicitud
						if (Constants.ID_SORTEO.equals(campo) || Constants.ID_SORTEO_DETALLE.equals(campo)) {

							// se configua el ID de la serie o el sorteo y el campo(ID_SORTEO,ID_SORTEO_DETALLE)
							response.setIdSerieDetalle(Long.valueOf(Util.getValue(detalle, Numero.TRES.valueI)));
							response.setEsSolicitudTodaLaSerie(Constants.ID_SORTEO.equals(campo));

							// si el campo es ID_SORTEO_DETALLE se obtiene su hora y fecha del sorteo
							if (Constants.ID_SORTEO_DETALLE.equals(campo)) {
								horaSorteo = Util.getValue(detalle, Numero.CUATRO.valueI);
								fechaSorteo = (Timestamp) detalle[Numero.CINCO.valueI];
							}
						} else if (Constants.REQUEST_JSON.equals(campo)) {
							json = Util.getValue(detalle, Numero.TRES.valueI);
						}
					}
					// se utiliza para obtener el DTO a partir de un JSON
					Gson apiGson = new Gson();

					// si el tipo de solicitud es de CREACION de sorteos
					if (response.isEsSolicitudCreacion()) {
						response.setDespues(apiGson.fromJson(json, AutorizacionCrearEditarSerieDTO.class));
					}

					// si el tipo de solicitud es de CANCELACION de toda la serie o solamente del sorteo
					else if (response.isEsSolicitudCancelacion()) {

						// datos despues de autorizar esta solicitud
						response.setDespues(apiGson.fromJson(json, AutorizacionCancelarSerieSorteoDTO.class));

						// datos antes de autorizar esta solicitud
						AutorizacionCancelarSerieSorteoDTO antes = apiGson.fromJson(json, AutorizacionCancelarSerieSorteoDTO.class);
						antes.setEstado(EstadoEnum.ACTIVO.name());
						response.setAntes(antes);
					}

					// si el tipo de solicitud es de MODIFICACION de toda la serie o solamente del sorteo
					else if (response.isEsSolicitudModificacion()) {

						// si es modificacion de toda la serie
						if (response.isEsSolicitudTodaLaSerie()) {

							// datos despues de autorizar esta solicitud
							response.setDespues(apiGson.fromJson(json, AutorizacionCrearEditarSerieDTO.class));

							// datos antes de autorizar esta solicitud
							Query query = this.em.createNativeQuery(SQLConstant.SELECT_ULTIMA_MOIFICACION_CALENDARIO_SORTEO);
							query.setParameter(Numero.UNO.valueI, response.getIdSerieDetalle().toString());
							List<String> resultJson = query.getResultList();
							if (resultJson != null && !resultJson.isEmpty()) {
								response.setAntes(apiGson.fromJson(resultJson.get(Numero.ZERO.valueI), AutorizacionCrearEditarSerieDTO.class));
							}
						}

						// si es modificacion de solo un sorteo
						else {
							// datos despues de autorizar esta solicitud
							response.setDespues(apiGson.fromJson(json, AutorizacionEditarSorteoDTO.class));

							// datos antes de autorizar esta solicitud
							AutorizacionEditarSorteoDTO antes = apiGson.fromJson(json, AutorizacionEditarSorteoDTO.class);
							antes.setFechaSorteo(fechaSorteo != null ? new Date(fechaSorteo.getTime()) : null);
							antes.setHoraSorteo(horaSorteo);
							response.setAntes(antes);
						}
					}
				}
			}
		}
		return response;
	}

	/**
	 * Servicio que permite rechazar una solicitud de calendario sorteos
	 * @param solicitud, DTO que contiene los datos de la solicitud a rechazar
	 */
	@Transactional
	public void rechazarSolicitudCalendarioSorteos(DetalleSolicitudCalendarioSorteoDTO solicitud) throws Exception {

		// los datos de la solicitud son requerido
		if (solicitud == null) {
			throw new BusinessException(MessagesBussinesKey.KEY_SOLICITUD_DATA_REQUERIDO.value);
		}

		// el usuario quien rechaza y el id de la solicitud son requeridos
		Long idUsuarioAutoriza = solicitud.getIdUsuarioAutoriza();
		Long idSolicitud = solicitud.getIdSolicitud();
		if (idUsuarioAutoriza == null || idSolicitud == null) {
			throw new BusinessException(MessagesBussinesKey.KEY_SOLICITUD_DATA_REQUERIDO.value);
		}

		// para el proceso de creacion el id del sorteo es requerido
		Long idSerieDetalle = solicitud.getIdSerieDetalle();
		if (solicitud.isEsSolicitudCreacion() && idSerieDetalle == null) {
			throw new BusinessException(MessagesBussinesKey.KEY_SOLICITUD_DATA_REQUERIDO.value);
		}

		// se obtiene la conection para trabajar con JDBC
		UtilJDBC utilJDBC = UtilJDBC.getInstance();
		Connection connection = utilJDBC.getConnection(this.em);
		try {
			// se cambia el estado de la solicitud a RECHAZADO
			ValueSQL estadoRechazado = ValueSQL.get(EstadoEnum.RECHAZADO.name(), Types.VARCHAR);
			utilJDBC.insertUpdate(connection,
					SQLConstant.UPDATE_ESTADO_AUTORIZACION,
					estadoRechazado,
					ValueSQL.get(idUsuarioAutoriza, Types.BIGINT),
					ValueSQL.get(idSolicitud, Types.BIGINT));

			// para el proceso de creacion se cambia el estado del sorteo y sus detalles a RECHAZADO
			if (solicitud.isEsSolicitudCreacion()) {

				// update para la tabla sorteo
				utilJDBC.insertUpdate(connection,
						SQLConstant.UPDATE_ESTADO_CALENDARIO_SORTEO,
						estadoRechazado,
						ValueSQL.get(idSerieDetalle, Types.BIGINT));

				// update para los detalles del sorteo
				utilJDBC.insertUpdate(connection,
						SQLConstant.UPDATE_ESTADO_DETALLE_CALENDARIO_SORTEO,
						estadoRechazado,
						ValueSQL.get(idSerieDetalle, Types.BIGINT));
			}
		} catch (Exception e) {
			connection.rollback();
			throw e;
		}
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
