package com.administracion.service;

import java.math.BigDecimal;
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
import com.administracion.dto.domicilios.DeliveryDTO;
import com.administracion.dto.domicilios.DomicilioValorDTO;
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
 * Service que contiene los procesos de negocio para los domicilios
 */
@Service
@SuppressWarnings("unchecked")
public class DomiciliosService {

	/** Contexto de la persistencia del sistema */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Servicio que soporta el proceso de negocio para obtener los VALORES
	 * de los domicilios parametrizados en el sistema
	 *
	 * @param filtro, DTO que contiene los valores del filtro de busqueda
	 * @return DTO con la lista de valores domicilios parametrizados en el sistema
	 */
	public PaginadorResponseDTO getDomiciliosValores(FiltroBusquedaDTO filtro) throws Exception {

		// se utiliza para encapsular la respuesta de esta peticion
		PaginadorResponseDTO response = new PaginadorResponseDTO();

		// se obtiene el from de la consulta
		StringBuilder sql = new StringBuilder(SQLConstant.SQL_GET_DOMICILIOS_VALORES);

		// contiene los valores de los parametros del los filtros
		ArrayList<Object> parametros = new ArrayList<>();

		// se configura los filtros de busqueda ingresados
		setFiltrosBusquedaDomiciliosValores(sql, parametros, filtro);

		// se ordena la consulta
		sql.append(SQLConstant.ORDER_DOMICILIOS_VALORES); 

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

			// se recorre cada valor
			DomicilioValorDTO domicilioValor;
			String idLocalidad;
			for (Object[] valores : result) {
				domicilioValor = new DomicilioValorDTO();
				domicilioValor.setId(Long.valueOf(Util.getValue(valores, Numero.ZERO.valueI)));
				domicilioValor.setZona(Util.getValue(valores, Numero.UNO.valueI));
				domicilioValor.setValor((BigDecimal) valores[Numero.DOS.valueI]);
				domicilioValor.setIdEstado(Util.getValue(valores, Numero.TRES.valueI));
				idLocalidad = Util.getValue(valores, Numero.CUATRO.valueI);
				domicilioValor.setIdLocalidad(idLocalidad != null ? Long.valueOf(idLocalidad) : null);
				domicilioValor.setLocalidad(Util.getValue(valores, Numero.CINCO.valueI));
				domicilioValor.setIdCiudad(Long.valueOf(Util.getValue(valores, Numero.SEIS.valueI)));
				domicilioValor.setCiudad(Util.getValue(valores, Numero.SIETE.valueI));
				domicilioValor.setIdDepartamento(Long.valueOf(Util.getValue(valores, Numero.OCHO.valueI)));
				domicilioValor.setDepartamento(Util.getValue(valores, Numero.NUEVE.valueI));
				domicilioValor.setEstado(EstadoEnum.ACTIVO.name().equals(Util.getValue(valores, Numero.DIEZ.valueI)));
				response.agregarRegistro(domicilioValor);
			}
		}
		return response;
	}

	/**
	 * Servicio que permite crear un VALOR para los domicilios
	 * @param valor, DTO que contiene los datos del VALOR a crear
	 */
	@Transactional
	public void crearDomicilioValor(DomicilioValorDTO valor) throws Exception {

		// se verifica los datos de entrada para la creacion
		isDatosValidosDomicilioValor(valor, false);

		// se obtiene la conection para trabajar con JDBC
		UtilJDBC utilJDBC = UtilJDBC.getInstance();
		Connection connection = utilJDBC.getConnection(this.em);
		try {
			utilJDBC.insertUpdate(connection, SQLConstant.INSERT_DOMICILIOS_VALORES,
					ValueSQL.get(valor.getIdLocalidad(), Types.BIGINT),
					ValueSQL.get(valor.getIdCiudad(), Types.BIGINT),
					ValueSQL.get(valor.getZona(), Types.VARCHAR),
					ValueSQL.get(valor.getValor(), Types.DECIMAL),
					ValueSQL.get(valor.isEstado() ? EstadoEnum.ACTIVO.name() : EstadoEnum.INACTIVO.name(), Types.VARCHAR));
		} catch (Exception e) {
			connection.rollback();
			throw e;
		}
	}

	/**
	 * Servicio que permite editar un VALOR para los domicilios
	 * @param valor, DTO que contiene los datos del VALOR a editar
	 */
	@Transactional
	public void editarDomicilioValor(DomicilioValorDTO valor) throws Exception {

		// se verifica los datos de entrada para la edicion
		isDatosValidosDomicilioValor(valor, true);

		// se obtiene la conection para trabajar con JDBC
		UtilJDBC utilJDBC = UtilJDBC.getInstance();
		Connection connection = utilJDBC.getConnection(this.em);
		try {
			utilJDBC.insertUpdate(connection, SQLConstant.UPDATE_DOMICILIOS_VALORES,
					ValueSQL.get(valor.getIdLocalidad(), Types.BIGINT),
					ValueSQL.get(valor.getIdCiudad(), Types.BIGINT),
					ValueSQL.get(valor.getZona(), Types.VARCHAR),
					ValueSQL.get(valor.getValor(), Types.DECIMAL),
					ValueSQL.get(valor.isEstado() ? EstadoEnum.ACTIVO.name() : EstadoEnum.INACTIVO.name(), Types.VARCHAR),
					ValueSQL.get(valor.getId(), Types.BIGINT));
		} catch (Exception e) {
			connection.rollback();
			throw e;
		}
	}

	/**
	 * Servicio que soporta el proceso de negocio para obtener
	 * los DELIVERIES parametrizados en el sistema
	 *
	 * @param filtro, DTO que contiene los valores del filtro de busqueda
	 * @return DTO con la lista de deliveries parametrizados en el sistema
	 */
	public PaginadorResponseDTO getDeliveries(FiltroBusquedaDTO filtro) throws Exception {

		// se utiliza para encapsular la respuesta de esta peticion
		PaginadorResponseDTO response = new PaginadorResponseDTO();

		// se obtiene el from de la consulta
		StringBuilder sql = new StringBuilder(SQLConstant.SQL_GET_DELIVERIES);

		// contiene los valores de los parametros del los filtros
		ArrayList<Object> parametros = new ArrayList<>();

		// se configura los filtros de busqueda ingresados
		setFiltrosBusquedaDelivery(sql, parametros, filtro);

		// se ordena la consulta
		sql.append(SQLConstant.ORDER_DELIVERIES);

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

			// se recorre cada delivery
			DeliveryDTO delivery;
			for (Object[] valores : result) {
				delivery = new DeliveryDTO();
				delivery.setId(Long.valueOf(Util.getValue(valores, Numero.ZERO.valueI)));
				delivery.setNombre(Util.getValue(valores, Numero.UNO.valueI));
				delivery.setCorreo(Util.getValue(valores, Numero.DOS.valueI));
				delivery.setTelefono(Util.getValue(valores, Numero.TRES.valueI));
				delivery.setEstado(Util.getValue(valores, Numero.CUATRO.valueI));
				response.agregarRegistro(delivery);
			}
		}
		return response;
	}

	/**
	 * Metodo que permite configurar los filtros de busqueda para
	 * obtener los DOMICILIOS VALORES
	 */
	private void setFiltrosBusquedaDomiciliosValores(
			StringBuilder sql,
			ArrayList<Object> parametros,
			FiltroBusquedaDTO filtro) {

		// filtro por el estado del VALOR
		Boolean estado = filtro.getEstado();
		if (estado != null) {
			sql.append("WHERE DV.ID_ESTADO=?");
			parametros.add(estado.booleanValue() ? EstadoEnum.ACTIVO.name() : EstadoEnum.INACTIVO.name());
		}

		// filtro por el id del departamento
		Long idDepartamento = filtro.getIdDepartamento();
		if (idDepartamento != null && !idDepartamento.equals(Numero.ZERO.valueL)) {
			sql.append(parametros.size() > Numero.ZERO.valueI.intValue() ? " AND " : " WHERE ");
			sql.append("DE.ID_DEPARTAMENTO=?");
			parametros.add(idDepartamento);
		}

		// filtro por el id de la ciudad
		Long idCiudad = filtro.getIdCiudad();
		if (idCiudad != null && !idCiudad.equals(Numero.ZERO.valueL)) {
			sql.append(parametros.size() > Numero.ZERO.valueI.intValue() ? " AND " : " WHERE ");
			sql.append("CI.ID_CIUDAD=?");
			parametros.add(idCiudad);
		}
	}

	/**
	 * Metodo que permite configurar los filtros de busqueda para
	 * obtener los DELIVERIES
	 */
	private void setFiltrosBusquedaDelivery(
			StringBuilder sql,
			ArrayList<Object> parametros,
			FiltroBusquedaDTO filtro) {

		// filtro por el estado del VALOR
		Boolean estado = filtro.getEstado();
		if (estado != null) {
			sql.append("WHERE D.ID_ESTADO=?");
			parametros.add(estado.booleanValue() ? EstadoEnum.ACTIVO.name() : EstadoEnum.INACTIVO.name());
		}

		// filtro por el nombre
		String nombre = filtro.getNombre();
		if (!Util.isNull(nombre)) {
			sql.append(parametros.size() > Numero.ZERO.valueI.intValue() ? " AND " : " WHERE ");
			sql.append("UPPER(CONCAT(P.PRIMER_NOMBRE,' ',P.SEGUNDO_NOMBRE,' ',P.PRIMER_APELLIDO,' ',P.SEGUNDO_APELLIDO)) LIKE UPPER(?)");
			parametros.add("%" + nombre + "%");
		}
	}

	/**
	 * Verifica los datos de entrada para la creacion o edicion de los valores domicilio
	 */
	private void isDatosValidosDomicilioValor(DomicilioValorDTO valor, boolean isEdicion) throws Exception {

		// los datos del VALOR son requerido
		if (valor == null) {
			throw new BusinessException(MessagesBussinesKey.KEY_SOLICITUD_DATA_REQUERIDO.value);
		}

		// se verifica cada valor
		Long idValor = valor.getId();
		Long idLocalidad = valor.getIdLocalidad();
		Long idCiudad = valor.getIdCiudad();
		String zona = valor.getZona();
		if (valor.getValor() == null ||
			idCiudad == null ||
			Util.isNull(zona) ||
			(isEdicion && idValor == null)) {
			throw new BusinessException(MessagesBussinesKey.KEY_SOLICITUD_DATA_REQUERIDO.value);
		}

		// no puede existir dos domicilios valores para la misma localidad o ciudad
		String sqlCount = SQLConstant.COUNT_DOMICILIOS_VALORES_CIUDAD;
		Long idFiltro = idCiudad;
		if (idLocalidad != null) {
			sqlCount = SQLConstant.COUNT_DOMICILIOS_VALORES_LOCALIDAD;
			idFiltro = idLocalidad;
		}
		Query qcount = this.em.createNativeQuery(isEdicion ? (sqlCount + " AND ID_VALOR<>?") : sqlCount);
		qcount.setParameter(Numero.UNO.valueI, idFiltro);
		qcount.setParameter(Numero.DOS.valueI, zona);
		if (isEdicion) {
			qcount.setParameter(Numero.TRES.valueI, idValor);
		}
		if (((BigInteger) qcount.getSingleResult()).intValue() > Numero.ZERO.valueI) {
			throw new BusinessException(MessagesBussinesKey.KEY_LOCALIDAD_DOMICILIO_VALOR_YA_EXISTE.value);
		}
	}
}
