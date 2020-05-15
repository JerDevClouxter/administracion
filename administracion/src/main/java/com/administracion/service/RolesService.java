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

import com.administracion.constant.Constants;
import com.administracion.constant.MessagesBussinesKey;
import com.administracion.constant.SQLConstant;
import com.administracion.constant.SQLTransversal;
import com.administracion.dto.roles.RolDTO;
import com.administracion.dto.roles.RolRecursoDTO;
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
		StringBuilder sql = new StringBuilder(SQLConstant.SQL_GET_ROLES);

		// contiene los valores de los parametros del los filtros
		ArrayList<Object> parametros = new ArrayList<>();

		// se configura los filtros de busqueda ingresados
		setFiltrosBusquedaRoles(sql, parametros, filtro);

		// se agrupa y se ordena la consulta
		sql.append(SQLConstant.ORDER_GROUP_ROLES); 

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

			// se recorre cada role
			RolDTO rol;
			for (Object[] roles : result) {
				rol = new RolDTO();
				rol.setId(Long.valueOf(Util.getValue(roles, Numero.ZERO.valueI)));
				rol.setNombre(Util.getValue(roles, Numero.UNO.valueI));
				rol.setDescripcion(Util.getValue(roles, Numero.DOS.valueI));
				rol.setNombreEstado(Util.getValue(roles, Numero.TRES.valueI));
				rol.setVerEmpresas(Util.getValue(roles, Numero.CUATRO.valueI));
				rol.setVerRecursos(Util.getValue(roles, Numero.CINCO.valueI));
				response.agregarRegistro(rol);
			}
		}
		return response;
	}

	/**
	 * Servicio que permite crear un ROL en el sistema
	 * @param rol, DTO que contiene los datos del ROL a crear
	 */
	@Transactional
	public void crearRol(RolDTO rol) throws Exception {

		// se verifica los datos de entrada para la creacion
		isDatosValidosCrearRol(rol);

		// se obtiene la conection para trabajar con JDBC
		UtilJDBC utilJDBC = UtilJDBC.getInstance();
		Connection connection = utilJDBC.getConnection(this.em);
		try {
			// se procede a crear el registro en la tabla padre ROLES
			Long idRol = utilJDBC.insertReturningID(
					connection, SQLConstant.INSERT_ROLES,
					ValueSQL.get(rol.getNombre(), Types.VARCHAR),
					ValueSQL.get(rol.getDescripcion(), Types.VARCHAR),
					ValueSQL.get(rol.isEstado() ? EstadoEnum.ACTIVO.name() : EstadoEnum.INACTIVO.name(), Types.VARCHAR));

			// contiene los DMLS para ser ejecutado por el BATCH JDBC
			List<String> dmls = new ArrayList<>();
			StringBuilder dml;

			// se verifica si hay empresas asociadas al ROLE
			List<Long> empresas = rol.getEmpresas();
			if (empresas != null && !empresas.isEmpty()) {
				for (Long idEmpresa : empresas) {
					dml = new StringBuilder(SQLConstant.INSERT_EMPRESAS_ROLES);
					dml.append(idRol).append(Constants.COMA);
					dml.append(idEmpresa).append(Constants.COMA);
					dml.append(Constants.COMILLA_SIMPLE).append(EstadoEnum.ACTIVO.name());
					dml.append(Constants.COMILLA_SIMPLE).append(Constants.PARENT_CIERRE);
					dmls.add(dml.toString());
				}
			}

			// se procede a crear los recursos asociados al ROLE
			List<RolRecursoDTO> recursos = rol.getRecursos();
			for (RolRecursoDTO recurso : recursos) {
				for (Long idAccion : recurso.getIdAcciones()) {
					dml = new StringBuilder(SQLConstant.INSERT_ROLES_RECURSOS_ACCIONES);
					dml.append(idRol).append(Constants.COMA);
					dml.append(recurso.getId()).append(Constants.COMA);
					dml.append(idAccion).append(Constants.COMA);
					dml.append(Constants.COMILLA_SIMPLE).append(EstadoEnum.ACTIVO.name());
					dml.append(Constants.COMILLA_SIMPLE).append(Constants.PARENT_CIERRE);
					dmls.add(dml.toString());
				}
			}

			// se ejecuta el bach para todos los insert de las empresas y recursos
			utilJDBC.batchSinInjection(connection, dmls);
		} catch (Exception e) {
			connection.rollback();
			throw e;
		}
	}

	/**
	 * Servicio que permite obtener los detalles de un ROL
	 *
	 * @param idRol, identificador del ROL
	 * @return, datos con todos los detalles del ROL
	 */
	public RolDTO getDetalleRol(Long idRol) throws Exception {

		// DTO con los datos del response
		RolDTO detalle = null;

		// debe existir el identificador del ROL para la busqueda
		if (idRol != null) {

			// se procede a consultar los detalles de este ROL
			Query q = this.em.createNativeQuery(SQLConstant.SQL_DETALLE_ROL);
			q.setParameter(Numero.UNO.valueI, idRol);
			List<Object[]> result = q.getResultList();

			// se verifica si hay detalles asociados al ID
			if (result != null && !result.isEmpty()) {

				// se recorre cada registro configurando los datos del detalle del ROL
				String empresas;
				detalle = new RolDTO();
				String[] split;
				for (Object[] item : result) {

					// se verifica si es la primera iteraccion
					if (detalle.getId() == null) {
						detalle.setId(Long.valueOf(Util.getValue(item, Numero.ZERO.valueI)));
						detalle.setNombre(Util.getValue(item, Numero.UNO.valueI));
						detalle.setDescripcion(Util.getValue(item, Numero.DOS.valueI));
						detalle.setEstado(EstadoEnum.ACTIVO.name().equals(Util.getValue(item, Numero.TRES.valueI)));
						empresas = Util.getValue(item, Numero.SEIS.valueI);
						if (!Util.isNull(empresas)) {
							split = empresas.split(Constants.COMA);
							for (String idEmpresa : split) {
								detalle.agregarEmpresa(idEmpresa);
							}
						}
					}

					// se agregar el recurso para este detalle
					detalle.agregarRecurso(
							Util.getValue(item, Numero.CUATRO.valueI),
							Util.getValue(item, Numero.CINCO.valueI));
				}
			}
		}
		return detalle;
	}

	/**
	 * Servicio que permite soportar el proceso de negocio para la edicion del ROL
	 * @param rol, DTO que contiene los datos del ROL a modificar
	 */
	@Transactional
	public void editarRol(RolDTO rol) throws Exception {

		// los datos del ROLE son requerido
		if (rol == null || rol.getId() == null) {
			throw new BusinessException(MessagesBussinesKey.KEY_SOLICITUD_DATA_REQUERIDO.value);
		}

		// se verifica si los datos generales son validos si fueron modificados
		if (rol.isInfoGeneralModificado()) {
			isDatosGeneralValidos(rol, true);
		}

		// se verifica la nulalidad de los recursos si fueron modificados
		List<RolRecursoDTO> recursos = rol.getRecursos();
		if (rol.isRecursosModificado()) {
			if (recursos == null || recursos.isEmpty()) {
				throw new BusinessException(MessagesBussinesKey.KEY_SOLICITUD_DATA_REQUERIDO.value);
			}
		}

		// se obtiene la conection para trabajar con JDBC
		UtilJDBC utilJDBC = UtilJDBC.getInstance();
		Connection connection = utilJDBC.getConnection(this.em);
		try {
			Long idRol = rol.getId();

			// se edita las informacion general si fueron modificados
			if (rol.isInfoGeneralModificado()) {
				utilJDBC.insertUpdate(connection,
						SQLConstant.UPDATE_ROLE_INFO_GENERAL,
						ValueSQL.get(rol.getNombre(), Types.VARCHAR),
						ValueSQL.get(rol.getDescripcion(), Types.VARCHAR),
						ValueSQL.get(rol.isEstado() ? EstadoEnum.ACTIVO.name() : EstadoEnum.INACTIVO.name(), Types.VARCHAR),
						ValueSQL.get(idRol, Types.BIGINT));
			}

			// contiene los DMLS para ser ejecutado por el BATCH JDBC
			List<String> dmls = new ArrayList<>();
			StringBuilder dml;

			// se verifica si las companias fueron modificadas
			if (rol.isCompaniasModificado()) {

				// se cancelan todas las companias asociadas al ROL
				dmls.add(new StringBuilder(SQLConstant.CANCELAR_ROLES_EMPRESAS).append(idRol).toString());

				// constantes necesarios para el proceso
				final String AND_EMPRESA = " AND ID_EMPRESA=";
				final String WHERE_NOT_EXISTS = " WHERE NOT EXISTS(SELECT * FROM ROLES_EMPRESAS WHERE ID_ROL=";

				// se verifica si hay empresas asociadas al ROL
				List<Long> empresas = rol.getEmpresas();
				if (empresas != null && !empresas.isEmpty()) {

					// se construye cada DML para asociar las empresas con el ROL
					for (Long idEmpresa : empresas) {

						// se actualiza esta empresa como ACTIVA
						dml = new StringBuilder(SQLConstant.ACTIVAR_EMPRESA_ROL);
						dml.append(idRol);
						dml.append(AND_EMPRESA).append(idEmpresa);
						dmls.add(dml.toString());

						// se crear el registro de ROL-EMPRESA solo si no existe
						dml = new StringBuilder(SQLConstant.INSERT_EMPRESAS_ROLES_EDICION);
						dml.append(idRol).append(Constants.COMA);
						dml.append(idEmpresa).append(Constants.COMA);
						dml.append(Constants.COMILLA_SIMPLE).append(EstadoEnum.ACTIVO.name()).append(Constants.COMILLA_SIMPLE);
						dml.append(WHERE_NOT_EXISTS);
						dml.append(idRol);
						dml.append(AND_EMPRESA).append(idEmpresa);
						dml.append(Constants.PARENT_CIERRE);
						dmls.add(dml.toString());
					}
				}
			}

			// se verifica si los recursos fueron modificadas
			if (rol.isRecursosModificado()) {

				// se eliminan todos los recursos asociados al ROL
				dmls.add(new StringBuilder(SQLConstant.DELETE_ROLES_RECURSOS).append(idRol).toString());

				// se construye cada INSERT para asociar los recursos al ROL
				for (RolRecursoDTO recurso : recursos) {
					for (Long idAccion : recurso.getIdAcciones()) {
						dml = new StringBuilder(SQLConstant.INSERT_ROLES_RECURSOS_ACCIONES);
						dml.append(idRol).append(Constants.COMA);
						dml.append(recurso.getId()).append(Constants.COMA);
						dml.append(idAccion).append(Constants.COMA);
						dml.append(Constants.COMILLA_SIMPLE).append(EstadoEnum.ACTIVO.name());
						dml.append(Constants.COMILLA_SIMPLE).append(Constants.PARENT_CIERRE);
						dmls.add(dml.toString());
					}
				}
			}

			// se verifica si se debe ejecutar el batch
			if (!dmls.isEmpty()) {
				utilJDBC.batchSinInjection(connection, dmls);
			}
		} catch (Exception e) {
			connection.rollback();
			throw e;
		}
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
		Boolean estado = filtro.getEstado();
		if (estado != null) {
			String idEstado = estado.booleanValue() ? EstadoEnum.ACTIVO.name() : EstadoEnum.INACTIVO.name();
			from.append(parametros.size() > Numero.ZERO.valueI.intValue() ? " AND " : " WHERE ");
			from.append("R.ID_ESTADO=?");
			parametros.add(idEstado);
		}

		// filtro por nombre del ROLE
		String nombre = filtro.getNombre();
		if (!Util.isNull(nombre)) {
			from.append(parametros.size() > Numero.ZERO.valueI.intValue() ? " AND " : " WHERE ");
			from.append("UPPER(R.NOMBRE)=UPPER(?)");
			parametros.add(nombre);
		}
	}

	/**
	 * Metodo que permite validar los datos del ROL para la creacion
	 */
	private void isDatosValidosCrearRol(RolDTO rol) throws Exception {

		// los datos del ROLE son requerido
		if (rol == null) {
			throw new BusinessException(MessagesBussinesKey.KEY_SOLICITUD_DATA_REQUERIDO.value);
		}

		// los recursos asociadas al role son requeridas
		List<RolRecursoDTO> recursos = rol.getRecursos();
		if (recursos == null || recursos.isEmpty()) {
			throw new BusinessException(MessagesBussinesKey.KEY_SOLICITUD_DATA_REQUERIDO.value);
		}

		// se verifica los datos generales del ROL
		isDatosGeneralValidos(rol, false);
	}

	/**
	 * Permite validar si los datos generales del ROL son validos para la edicion
	 * @param isEdicion, indica si el proceso es edicion
	 */
	private void isDatosGeneralValidos(RolDTO rol, boolean isEdicion) throws Exception {

		// se valida la nulalida del nombre, la descripcion y el estado del ROL
		String nombre = rol.getNombre();
		String descripcion = rol.getDescripcion();
		if (Util.isNull(nombre) || Util.isNull(descripcion)) {
			throw new BusinessException(MessagesBussinesKey.KEY_SOLICITUD_DATA_REQUERIDO.value);
		}

		// longitud del nombre 50
		final int LONGITUD_NOMBRE = 50;
		if (nombre.length() > LONGITUD_NOMBRE) {
			throw new BusinessException(MessagesBussinesKey.KEY_LONGITUD_NO_PERMITIDA.value);
		}

		// longitud de la descripcion 255
		final int LONGITUD_DESCRIPCION = 255;
		if (descripcion.length() > LONGITUD_DESCRIPCION) {
			throw new BusinessException(MessagesBussinesKey.KEY_LONGITUD_NO_PERMITIDA.value);
		}

		// se verifica si el nombre del ROL ya existe
		Query qcount = this.em.createNativeQuery(isEdicion
				? SQLConstant.COUNT_NOMBRE_ROL_EDICION 
				: SQLConstant.COUNT_NOMBRE_ROL_CREACION);
		qcount.setParameter(Numero.UNO.valueI, nombre);
		if (isEdicion) {
			qcount.setParameter(Numero.DOS.valueI, rol.getId());
		}
		if (((BigInteger) qcount.getSingleResult()).intValue() > Numero.ZERO.valueI) {
			throw new BusinessException(MessagesBussinesKey.KEY_NOMBRE_ROL_YA_EXISTE.value);
		}
	}
}
