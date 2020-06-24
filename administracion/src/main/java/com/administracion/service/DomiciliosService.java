package com.administracion.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
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
import com.administracion.dto.domicilios.DeliveryEquipoDTO;
import com.administracion.dto.domicilios.DeliveryVehiculoDTO;
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
				delivery.setNombreCompleto(Util.getValue(valores, Numero.UNO.valueI));
				delivery.setCorreo(Util.getValue(valores, Numero.DOS.valueI));
				delivery.setTelefono(Util.getValue(valores, Numero.TRES.valueI));
				delivery.setIdEstado(Util.getValue(valores, Numero.CUATRO.valueI));
				response.agregarRegistro(delivery);
			}
		}
		return response;
	}

	/**
	 * Servicio que permite crear un DELIVERY en el sistema
	 * @param delivery, DTO que contiene los datos del DELIVERY a crear
	 */
	@Transactional
	public void crearDelivery(DeliveryDTO delivery) throws Exception {

		// los datos del DELIVERY son requerido
		if (delivery == null) {
			throw new BusinessException(MessagesBussinesKey.KEY_SOLICITUD_DATA_REQUERIDO.value);
		}

		// se verifica si los datos personales son validos
		isDatosPersonalesDeliveryValidos(delivery, false);

		// se verifica si los datos del vehiculo son validos
		isDatosVehiculoDeliveryValidos(delivery, false);

		// se verifica si los datos del equipo son validos
		isDatosEquipoDeliveryValidos(delivery, false);

		// se obtiene la conection para trabajar con JDBC
		UtilJDBC utilJDBC = UtilJDBC.getInstance();
		Connection connection = utilJDBC.getConnection(this.em);
		try {
			// se realiza el insert para PESONAS
			Long idPersona = utilJDBC.insertReturningID(connection, SQLConstant.INSERT_DELIVERY_PERSONA,
					ValueSQL.get(delivery.getIdTipoDocumento(), Types.VARCHAR),
					ValueSQL.get(delivery.getNroDocumento(), Types.VARCHAR),
					ValueSQL.get(delivery.getPrimerNombre(), Types.VARCHAR),
					ValueSQL.get(delivery.getSegundoNombre(), Types.VARCHAR),
					ValueSQL.get(delivery.getPrimerApellido(), Types.VARCHAR),
					ValueSQL.get(delivery.getSegundoApellido(), Types.VARCHAR),
					ValueSQL.get(delivery.getTelefono(), Types.VARCHAR),
					ValueSQL.get(delivery.getCorreo(), Types.VARCHAR),
					ValueSQL.get(delivery.getFechaNacimiento(), Types.DATE),
					ValueSQL.get(delivery.getIdCiudadNacimiento(), Types.BIGINT),
					ValueSQL.get(delivery.getGenero(), Types.VARCHAR));
			ValueSQL idDelivery = ValueSQL.get(idPersona, Types.BIGINT);

			// se realiza el insert para la tabla DELIVERIES
			utilJDBC.insertUpdate(connection, SQLConstant.INSERT_DELIVERY,
				idDelivery,
				ValueSQL.get(delivery.isEstado() ? EstadoEnum.ACTIVO.name() : EstadoEnum.INACTIVO.name(), Types.VARCHAR));

			// se realiza el insert del equipo asignado
			DeliveryEquipoDTO equipoAsignado = delivery.getEquipoAsignado();
			if (equipoAsignado != null) {
				utilJDBC.insertUpdate(connection, SQLConstant.INSERT_DELIVERY_EQUIPO,
					idDelivery,
					ValueSQL.get(equipoAsignado.getIdFabricante(), Types.BIGINT),
					ValueSQL.get(equipoAsignado.getModelo(), Types.VARCHAR),
					ValueSQL.get(equipoAsignado.getNroSim(), Types.VARCHAR),
					ValueSQL.get(equipoAsignado.getNroImei(), Types.VARCHAR));
			}

			// se realiza el insert para el vehiculo del delivery
			DeliveryVehiculoDTO vehiculo = delivery.getVehiculo();
			if (vehiculo != null) {
				utilJDBC.insertUpdate(connection, SQLConstant.INSERT_DELIVERY_VEHICULO,
					idDelivery,
					ValueSQL.get(vehiculo.getPlaca(), Types.VARCHAR),
					ValueSQL.get(vehiculo.getIdTipoVehiculo(), Types.BIGINT),
					ValueSQL.get(vehiculo.getIdFabricante(), Types.BIGINT),
					ValueSQL.get(vehiculo.getCilindraje(), Types.VARCHAR));
			}
		} catch (Exception e) {
			connection.rollback();
			throw e;
		}
	}

	/**
	 * Servicio que permite obtener los detalles de un DELIVERY
	 *
	 * @param idDelivery, identificador del DELIVERY
	 * @return, datos con todos los detalles del DELIVERY
	 */
	public DeliveryDTO getDetalleDelivery(Long idDelivery) throws Exception {

		// DTO con los datos del response
		DeliveryDTO detalle = null;

		// debe existir el identificador del DELIVERY para la busqueda
		if (idDelivery != null) {

			// se procede a consultar los detalles de este delivery
			Query q = this.em.createNativeQuery(SQLConstant.GET_DETALLE_DELIVERY);
			q.setParameter(Numero.UNO.valueI, idDelivery);
			List<Object[]> result = q.getResultList();

			// se verifica si hay detalles asociados al ID
			if (result != null && !result.isEmpty()) {

				// se configura los datos del detalle del delivery
				String id;
				detalle = new DeliveryDTO();
				Object[] data = (Object[]) result.get(Numero.ZERO.valueI);
				detalle.setId(Long.valueOf(Util.getValue(data, Numero.ZERO.valueI)));
				detalle.setIdTipoDocumento(Util.getValue(data, Numero.UNO.valueI));
				detalle.setTipoDocumento(Util.getValue(data, Numero.DOS.valueI));
				detalle.setNroDocumento(Util.getValue(data, Numero.TRES.valueI));
				detalle.setPrimerNombre(Util.getValue(data, Numero.CUATRO.valueI));
				detalle.setSegundoNombre(Util.getValue(data, Numero.CINCO.valueI));
				detalle.setPrimerApellido(Util.getValue(data, Numero.SEIS.valueI));
				detalle.setSegundoApellido(Util.getValue(data, Numero.SIETE.valueI));
				detalle.setCorreo(Util.getValue(data, Numero.OCHO.valueI));
				detalle.setTelefono(Util.getValue(data, Numero.NUEVE.valueI));
				detalle.setFechaNacimiento((Date) data[Numero.DIEZ.valueI]);
				detalle.setIdCiudadNacimiento(Long.valueOf(Util.getValue(data, Numero.ONCE.valueI)));
				detalle.setCiudadNacimiento(Util.getValue(data, Numero.DOCE.valueI));
				detalle.setGenero(Util.getValue(data, Numero.TRECE.valueI));
				detalle.setEstado(EstadoEnum.ACTIVO.name().equals(Util.getValue(data, Numero.CATORCE.valueI)));
				id = Util.getValue(data, Numero.QUINCE.valueI);
				detalle.setEquipoAsignado(new DeliveryEquipoDTO());
				if (!Util.isNull(id)) {
					detalle.getEquipoAsignado().setId(Long.valueOf(id));
				}
				id = Util.getValue(data, Numero.DIECISEIS.valueI);
				if (!Util.isNull(id)) {
					detalle.getEquipoAsignado().setIdFabricante(Long.valueOf(id));
				}
				detalle.getEquipoAsignado().setFabricante(Util.getValue(data, Numero.DIECISIETE.valueI));
				detalle.getEquipoAsignado().setModelo(Util.getValue(data, Numero.DIECIOCHO.valueI));
				detalle.getEquipoAsignado().setNroSim(Util.getValue(data, Numero.DIECINUEVE.valueI));
				detalle.getEquipoAsignado().setNroImei(Util.getValue(data, Numero.VEINTE.valueI));
				id = Util.getValue(data, Numero.VEINTEUNO.valueI);
				detalle.setVehiculo(new DeliveryVehiculoDTO());
				if (!Util.isNull(id)) {
					detalle.getVehiculo().setId(Long.valueOf(id));
				}
				id = Util.getValue(data, Numero.VEINTEDOS.valueI);
				if (!Util.isNull(id)) {
					detalle.getVehiculo().setIdTipoVehiculo(Long.valueOf(id));
				}
				detalle.getVehiculo().setTipoVehiculo(Util.getValue(data, Numero.VEINTETRES.valueI));
				detalle.getVehiculo().setPlaca(Util.getValue(data, Numero.VEINTECUATRO.valueI));
				id = Util.getValue(data, Numero.VEINTECINCO.valueI);
				if (!Util.isNull(id)) {
					detalle.getVehiculo().setIdFabricante(Long.valueOf(id));
				}
				detalle.getVehiculo().setFabricante(Util.getValue(data, Numero.VEINTESEIS.valueI));
				detalle.getVehiculo().setCilindraje(Util.getValue(data, Numero.VEINTESIETE.valueI));
			}
		}
		return detalle;
	}

	/**
	 * Servicio que permite editar un DELIVERY en el sistema
	 * @param delivery, DTO que contiene los datos del DELIVERY a editar
	 */
	public void editarDelivery(DeliveryDTO delivery) throws Exception {

		// los datos del DELIVERY son requerido
		if (delivery == null) {
			throw new BusinessException(MessagesBussinesKey.KEY_SOLICITUD_DATA_REQUERIDO.value);
		}

		// se valida si los datos personales son validos si son modificados
		if (delivery.isDatosPersonalesModificado()) {
			isDatosPersonalesDeliveryValidos(delivery, true);
		}

		// se valida si los datos del vehiculo son validos si son modificados
		DeliveryVehiculoDTO vehiculo = delivery.getVehiculo();
		if (vehiculo != null && vehiculo.isDatosVehiculoModificado()) {
			isDatosVehiculoDeliveryValidos(delivery, true);
		}

		// se valida si los datos del equipo son validos si son modificados
		DeliveryEquipoDTO equipoAsignado = delivery.getEquipoAsignado();
		if (equipoAsignado != null && equipoAsignado.isDatosEquipoModificado()) {
			isDatosEquipoDeliveryValidos(delivery, true);
		}

		// se obtiene la conection para trabajar con JDBC
		UtilJDBC utilJDBC = UtilJDBC.getInstance();
		Connection connection = utilJDBC.getConnection(this.em);
		try {

			// se actualiza los datos personales si fueron modificados
			if (delivery.isDatosPersonalesModificado()) {
				ValueSQL idDelivery = ValueSQL.get(delivery.getId(), Types.BIGINT);

				// update para los datos personales
				utilJDBC.insertUpdate(connection, SQLConstant.UPDATE_DELIVERY_PERSONA,
						ValueSQL.get(delivery.getIdTipoDocumento(), Types.VARCHAR),
						ValueSQL.get(delivery.getNroDocumento(), Types.VARCHAR),
						ValueSQL.get(delivery.getPrimerNombre(), Types.VARCHAR),
						ValueSQL.get(delivery.getSegundoNombre(), Types.VARCHAR),
						ValueSQL.get(delivery.getPrimerApellido(), Types.VARCHAR),
						ValueSQL.get(delivery.getSegundoApellido(), Types.VARCHAR),
						ValueSQL.get(delivery.getTelefono(), Types.VARCHAR),
						ValueSQL.get(delivery.getCorreo(), Types.VARCHAR),
						ValueSQL.get(delivery.getFechaNacimiento(), Types.DATE),
						ValueSQL.get(delivery.getIdCiudadNacimiento(), Types.BIGINT),
						ValueSQL.get(delivery.getGenero(), Types.VARCHAR),
						idDelivery);

				// update para los datos del delivery
				utilJDBC.insertUpdate(connection, SQLConstant.UPDATE_DELIVERY,
						ValueSQL.get(delivery.isEstado() ? EstadoEnum.ACTIVO.name() : EstadoEnum.INACTIVO.name(), Types.VARCHAR),
						idDelivery);
			}

			// se actualiza los datos del equipo si fueron modificados
			if (equipoAsignado != null && equipoAsignado.isDatosEquipoModificado()) {
				utilJDBC.insertUpdate(connection, SQLConstant.UPDATE_DELIVERY_EQUIPO,
						ValueSQL.get(equipoAsignado.getIdFabricante(), Types.BIGINT),
						ValueSQL.get(equipoAsignado.getModelo(), Types.VARCHAR),
						ValueSQL.get(equipoAsignado.getNroSim(), Types.VARCHAR),
						ValueSQL.get(equipoAsignado.getNroImei(), Types.VARCHAR),
						ValueSQL.get(equipoAsignado.getId(), Types.BIGINT));
			}

			// se actualiza los datos del vehiculo si fueron modificados
			if (vehiculo != null && vehiculo.isDatosVehiculoModificado()) {
				utilJDBC.insertUpdate(connection, SQLConstant.UPDATE_DELIVERY_VEHICULO,
						ValueSQL.get(vehiculo.getPlaca(), Types.VARCHAR),
						ValueSQL.get(vehiculo.getIdTipoVehiculo(), Types.BIGINT),
						ValueSQL.get(vehiculo.getIdFabricante(), Types.BIGINT),
						ValueSQL.get(vehiculo.getCilindraje(), Types.VARCHAR),
						ValueSQL.get(vehiculo.getId(), Types.BIGINT));
			}
		} catch (Exception e) {
			connection.rollback();
			throw e;
		}
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

	/**
	 * Verifica si los datos personales del delivery son validos
	 */
	private void isDatosPersonalesDeliveryValidos(DeliveryDTO delivery, boolean isEdicion) throws Exception {

		// se verifica la nulalidad de cada valor
		Long idDelivery = delivery.getId();
		String idTipoDocumento = delivery.getIdTipoDocumento();
		String nroDocumento = delivery.getNroDocumento();
		String primerNombre = delivery.getPrimerNombre();
		String primerApellido = delivery.getPrimerApellido();
		String correo = delivery.getCorreo();
		String telefono = delivery.getTelefono();
		Date fechaNacimiento = delivery.getFechaNacimiento();
		Long idCiudadNacimiento = delivery.getIdCiudadNacimiento();
		String genero = delivery.getGenero();
		if (Util.isNull(idTipoDocumento) ||
			Util.isNull(nroDocumento) ||
			Util.isNull(primerNombre) || 
			Util.isNull(primerApellido) ||
			Util.isNull(correo) ||
			Util.isNull(telefono) ||
			Util.isNull(genero) ||
			fechaNacimiento == null ||
			idCiudadNacimiento == null ||
			(isEdicion && idDelivery == null)) {
			throw new BusinessException(MessagesBussinesKey.KEY_SOLICITUD_DATA_REQUERIDO.value);
		}

		// no puede existir otra persona con el mismo nro documento
		Query qcount = this.em.createNativeQuery(isEdicion
				? SQLConstant.COUNT_PERSONA_NRO_DOCUMENTO + " AND ID_PERSONA<>?"
				: SQLConstant.COUNT_PERSONA_NRO_DOCUMENTO);
		qcount.setParameter(Numero.UNO.valueI, nroDocumento);
		if (isEdicion) {
			qcount.setParameter(Numero.DOS.valueI, idDelivery);
		}
		if (((BigInteger) qcount.getSingleResult()).intValue() > Numero.ZERO.valueI) {
			throw new BusinessException(MessagesBussinesKey.KEY_PERSONA_MISMO_NRO_DOCUMENTO.value);
		}
	}

	/**
	 * Verifica si los datos del vehiculo del delivery son validos
	 */
	private void isDatosVehiculoDeliveryValidos(DeliveryDTO delivery, boolean isEdicion) throws Exception {

		// no puede existir una placa del vehiculo para otro delivery
		if (delivery.getVehiculo() != null) {
			String nroPlaca = delivery.getVehiculo().getPlaca();
			if (!Util.isNull(nroPlaca)) {
				Query qcount = this.em.createNativeQuery(isEdicion
						? SQLConstant.COUNT_PLACA_DELIVERY + " AND ID_DELIVERY<>?"
						: SQLConstant.COUNT_PLACA_DELIVERY);
				qcount.setParameter(Numero.UNO.valueI, nroPlaca);
				if (isEdicion) {
					qcount.setParameter(Numero.DOS.valueI, delivery.getId());
				}
				if (((BigInteger) qcount.getSingleResult()).intValue() > Numero.ZERO.valueI) {
					throw new BusinessException(MessagesBussinesKey.KEY_DELIVERY_PLACA_YA_EXISTE.value);
				}
			}
		}
	}

	/**
	 * Verifica si los datos del equipo del delivery son validos
	 */
	private void isDatosEquipoDeliveryValidos(DeliveryDTO delivery, boolean isEdicion) throws Exception {

		// se verifica el nro sim o imei del equipo asignado al delivery
		if (delivery.getEquipoAsignado() != null) {
			Long idDelivery = delivery.getId();

			// se obtiene los dos identificadores
			String sim = delivery.getEquipoAsignado().getNroSim();
			String imei = delivery.getEquipoAsignado().getNroImei();

			// se valida el nro SIM que no exista para otra delivery
			if (!Util.isNull(sim)) {
				Query qcount = this.em.createNativeQuery(isEdicion
						? SQLConstant.COUNT_NRO_SIM + " AND ID_DELIVERY<>?"
						: SQLConstant.COUNT_NRO_SIM);
				qcount.setParameter(Numero.UNO.valueI, sim);
				if (isEdicion) {
					qcount.setParameter(Numero.DOS.valueI, idDelivery);
				}
				if (((BigInteger) qcount.getSingleResult()).intValue() > Numero.ZERO.valueI) {
					throw new BusinessException(MessagesBussinesKey.KEY_DELIVERY_SIM_YA_EXISTE.value);
				}
			}

			// se valida el nro IMEI que no exista para otro delivery
			if (!Util.isNull(imei)) {
				Query qcount = this.em.createNativeQuery(isEdicion
						? SQLConstant.COUNT_NRO_IMEI + " AND ID_DELIVERY<>?"
						: SQLConstant.COUNT_NRO_IMEI);
				qcount.setParameter(Numero.UNO.valueI, imei);
				if (isEdicion) {
					qcount.setParameter(Numero.DOS.valueI, idDelivery);
				}
				if (((BigInteger) qcount.getSingleResult()).intValue() > Numero.ZERO.valueI) {
					throw new BusinessException(MessagesBussinesKey.KEY_DELIVERY_IMEI_YA_EXISTE.value);
				}
			}
		}
	}
}
