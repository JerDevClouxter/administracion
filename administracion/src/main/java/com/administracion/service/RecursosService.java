package com.administracion.service;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.administracion.constant.MessagesBussinesKey;
import com.administracion.constant.SQLConstant;
import com.administracion.constant.SQLTransversal;
import com.administracion.dto.recursos.RecursoDTO;
import com.administracion.dto.solicitudes.FiltroBusquedaDTO;
import com.administracion.dto.transversal.PaginadorDTO;
import com.administracion.dto.transversal.PaginadorResponseDTO;
import com.administracion.enums.EstadoEnum;
import com.administracion.enums.Numero;
import com.administracion.jdbc.UtilJDBC;
import com.administracion.jdbc.ValueSQL;
import com.administracion.util.BusinessException;
import com.administracion.util.Util;

/**
 * Service que contiene los procesos de negocio para la administracion de los RECURSOS
 */
@Service
@SuppressWarnings("unchecked")
public class RecursosService {

	/** Contexto de la persistencia del sistema */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Servicio que soporta el proceso de negocio para obtener los RECURSOS
	 * parametrizados en el sistema
	 *
	 * @param filtro, DTO que contiene los valores del filtro de busqueda
	 * @return DTO con la lista de RECURSOS parametrizados en el sistema
	 */
	public PaginadorResponseDTO getRecursos(FiltroBusquedaDTO filtro) throws Exception {

		// se utiliza para encapsular la respuesta de esta peticion
		PaginadorResponseDTO response = new PaginadorResponseDTO();

		// se obtiene el from de la consulta
		StringBuilder sql = new StringBuilder(SQLConstant.SQL_GET_RECURSOS);

		// contiene los valores de los parametros del los filtros
		ArrayList<Object> parametros = new ArrayList<>();

		// se configura los filtros de busqueda ingresados
		setFiltrosBusquedaRecursos(sql, parametros, filtro);

		// se ordena la consulta
		sql.append(SQLConstant.ORDER_RECURSOS); 

		// se utiliza para obtener los datos del paginador
		PaginadorDTO paginador = filtro.getPaginador();

		// se valida si se debe contar el total de los registros
		response.setCantidadTotal(paginador.getCantidadTotal());
		if (response.getCantidadTotal() == null) {

			// se configura el query con los valores de los filtros
			Query qcount = this.em.createNativeQuery(SQLTransversal.getSQLCountTbl(sql.toString()));
			Util.setParameters(qcount, parametros);

			// se configura la cantidad total de acuerdo al filtro de busqueda
			response.setCantidadTotal(((BigInteger) qcount.getSingleResult()).longValue());
		}

		// solo se consultan los registros solo si existen de acuerdo al filtro
		if (response.getCantidadTotal() != null &&
			!response.getCantidadTotal().equals(Numero.ZERO.valueL)) {

			// se configura la paginacion de la consulta
			SQLTransversal.getSQLPaginator(paginador.getSkip(), paginador.getRowsPage(), sql);

			// se configura el query con los valores de los filtros y se obtiene los datos
			Query query = this.em.createNativeQuery(sql.toString());
			Util.setParameters(query, parametros);
			List<Object[]> result = query.getResultList();

			// se recorre cada recurso
			RecursoDTO recurso;
			String valor;
			for (Object[] recursos : result) {
				recurso = new RecursoDTO();
				recurso.setId(Long.valueOf(Util.getValue(recursos, Numero.ZERO.valueI)));
				recurso.setNombre(Util.getValue(recursos, Numero.UNO.valueI));
				recurso.setDescripcion(Util.getValue(recursos, Numero.DOS.valueI));
				recurso.setUrl(Util.getValue(recursos, Numero.TRES.valueI));
				recurso.setIcono(Util.getValue(recursos, Numero.CUATRO.valueI));
				valor = Util.getValue(recursos, Numero.CINCO.valueI);
				recurso.setIdAplicacion(Util.isNull(valor) ? null : Integer.valueOf(valor));
				valor = Util.getValue(recursos, Numero.SEIS.valueI);
				recurso.setIdRecursoPadre(Util.isNull(valor) ? null : Long.valueOf(valor));
				recurso.setIdEstado(Util.getValue(recursos, Numero.SIETE.valueI));
				recurso.setNombreAplicacion(Util.getValue(recursos, Numero.OCHO.valueI));
				recurso.setNombreRecursoPadre(Util.getValue(recursos, Numero.NUEVE.valueI));
				recurso.setEstado(EstadoEnum.ACTIVO.name().equals(Util.getValue(recursos, Numero.DIEZ.valueI)));
				response.agregarRegistro(recurso);
			}
		}
		return response;
	}

	/**
	 * Servicio que permite crear un RECURSO en el sistema
	 * @param recurso, DTO que contiene los datos del RECURSO a crear
	 */
	@Transactional
	public void crearRecurso(RecursoDTO recurso) throws Exception {

		// se verifica los datos de entrada para la creacion
		isDatosValidosRecurso(recurso, false);

		// se obtiene la conection para trabajar con JDBC
		UtilJDBC utilJDBC = UtilJDBC.getInstance();
		Connection connection = utilJDBC.getConnection(this.em);
		try {
			utilJDBC.insertUpdate(connection, SQLConstant.INSERT_RECURSOS,
					ValueSQL.get(recurso.getNombre(), Types.VARCHAR),
					ValueSQL.get(recurso.getDescripcion(), Types.VARCHAR),
					ValueSQL.get(recurso.getUrl(), Types.VARCHAR),
					ValueSQL.get(recurso.getIdAplicacion(), Types.INTEGER),
					ValueSQL.get(recurso.getIcono(), Types.VARCHAR),
					ValueSQL.get(recurso.isEstado() ? EstadoEnum.ACTIVO.name() : EstadoEnum.INACTIVO.name(), Types.VARCHAR),
					ValueSQL.get(recurso.getIdRecursoPadre(), Types.BIGINT));
		} catch (Exception e) {
			connection.rollback();
			throw e;
		}
	}

	/**
	 * Servicio que permite editar un RECURSO en el sistema
	 * @param recurso, DTO que contiene los datos del RECURSO a editar
	 */
	@Transactional
	public void editarRecurso(RecursoDTO recurso) throws Exception {

		// se verifica los datos de entrada para la creacion
		isDatosValidosRecurso(recurso, true);

		// se obtiene la conection para trabajar con JDBC
		UtilJDBC utilJDBC = UtilJDBC.getInstance();
		Connection connection = utilJDBC.getConnection(this.em);
		try {
			utilJDBC.insertUpdate(connection, SQLConstant.UPDATE_RECURSOS,
					ValueSQL.get(recurso.getNombre(), Types.VARCHAR),
					ValueSQL.get(recurso.getDescripcion(), Types.VARCHAR),
					ValueSQL.get(recurso.getUrl(), Types.VARCHAR),
					ValueSQL.get(recurso.getIdAplicacion(), Types.INTEGER),
					ValueSQL.get(recurso.getIcono(), Types.VARCHAR),
					ValueSQL.get(recurso.isEstado() ? EstadoEnum.ACTIVO.name() : EstadoEnum.INACTIVO.name(), Types.VARCHAR),
					ValueSQL.get(recurso.getIdRecursoPadre(), Types.BIGINT),
					ValueSQL.get(recurso.getId(), Types.BIGINT));
		} catch (Exception e) {
			connection.rollback();
			throw e;
		}
	}

	/**
	 * Metodo que permite validar los datos del RECURSO para la creacion o edicion
	 */
	private void isDatosValidosRecurso(RecursoDTO recurso, boolean isEdicion) throws Exception {

		// los datos del RECURSO son requerido
		if (recurso == null) {
			throw new BusinessException(MessagesBussinesKey.KEY_SOLICITUD_DATA_REQUERIDO.value);
		}

		// se valida la nulalida de los datos generales del RECURSO
		String nombre = recurso.getNombre();
		String descripcion = recurso.getDescripcion();
		Long idRecurso = recurso.getId();
		if (Util.isNull(nombre) ||
			Util.isNull(descripcion) ||
			recurso.getIdAplicacion() == null ||
			(isEdicion && idRecurso == null)) {
			throw new BusinessException(MessagesBussinesKey.KEY_SOLICITUD_DATA_REQUERIDO.value);
		}

		// se verifica la longitud de cada dato
		final int LONGITUD_NOMBRE = 50;
		if (nombre.length() > LONGITUD_NOMBRE) {
			throw new BusinessException(MessagesBussinesKey.KEY_LONGITUD_NO_PERMITIDA.value);
		}
		final int LONGITUD_255 = 255;
		if (descripcion.length() > LONGITUD_255) {
			throw new BusinessException(MessagesBussinesKey.KEY_LONGITUD_NO_PERMITIDA.value);
		}
		String url = recurso.getUrl();
		boolean existeURL = !Util.isNull(url);
		if (existeURL && url.length() > LONGITUD_255) {
			throw new BusinessException(MessagesBussinesKey.KEY_LONGITUD_NO_PERMITIDA.value);
		}
		final int LONGITUD_ICONO = 100;
		String icono = recurso.getIcono();
		if (!Util.isNull(icono) && icono.length() > LONGITUD_ICONO) {
			throw new BusinessException(MessagesBussinesKey.KEY_LONGITUD_NO_PERMITIDA.value);
		}

		// No puede existir un recurso con el mismo NOMBRE y URL
		String andURL = existeURL ? " AND UPPER(URL)=UPPER(?)" : " AND URL IS NULL" ;
		Query qcount = this.em.createNativeQuery(isEdicion
				? (SQLConstant.COUNT_NOMBRE_URL_RECURSO + andURL + " AND ID_RECURSO<>?") 
				: (SQLConstant.COUNT_NOMBRE_URL_RECURSO + andURL));
		qcount.setParameter(Numero.UNO.valueI, nombre);
		if (existeURL) {
			qcount.setParameter(Numero.DOS.valueI, url);
		}
		if (isEdicion) {
			qcount.setParameter(existeURL ? Numero.TRES.valueI : Numero.DOS.valueI, idRecurso);
		}
		if (((BigInteger) qcount.getSingleResult()).intValue() > Numero.ZERO.valueI) {
			throw new BusinessException(MessagesBussinesKey.KEY_NOMBRE_URL_RECURSO_YA_EXISTE.value);
		}

		// el icono no puede contener espacio si lo tiene se toma el ultimo valor
		if (!Util.isNull(icono)) {
			String[] split = icono.split(" ");
			if (split.length > 1) {
				recurso.setIcono(split[1]);
			}
		}
	}

	/**
	 * Metodo que permite configurar los filtros de busqueda para
	 * obtener los RECURSOS parametrizados en el sistema
	 */
	private void setFiltrosBusquedaRecursos(
			StringBuilder sql,
			ArrayList<Object> parametros,
			FiltroBusquedaDTO filtro) {

		// filtro por el estado del RECURSO
		Boolean estado = filtro.getEstado();
		if (estado != null) {
			String idEstado = estado.booleanValue() ? EstadoEnum.ACTIVO.name() : EstadoEnum.INACTIVO.name();
			sql.append(parametros.size() > Numero.ZERO.valueI.intValue() ? " AND " : " WHERE ");
			sql.append("R.ID_ESTADO=?");
			parametros.add(idEstado);
		}

		// filtro por nombre del RECURSO
		String nombre = filtro.getNombre();
		if (!Util.isNull(nombre)) {
			sql.append(parametros.size() > Numero.ZERO.valueI.intValue() ? " AND " : " WHERE ");
			sql.append("UPPER(R.NOMBRE)=UPPER(?)");
			parametros.add(nombre);
		}
	}
}
