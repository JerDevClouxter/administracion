package com.administracion.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.administracion.constant.SQLConstant;
import com.administracion.constant.SQLTransversal;
import com.administracion.dto.domicilios.DomicilioValorDTO;
import com.administracion.dto.solicitudes.FiltroBusquedaDTO;
import com.administracion.dto.transversal.PaginadorDTO;
import com.administracion.dto.transversal.PaginadorResponseDTO;
import com.administracion.enums.EstadoEnum;
import com.administracion.enums.Numero;
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
	public PaginadorResponseDTO getValores(FiltroBusquedaDTO filtro) throws Exception {

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
			for (Object[] valores : result) {
				domicilioValor = new DomicilioValorDTO();
				domicilioValor.setId(Long.valueOf(Util.getValue(valores, Numero.ZERO.valueI)));
				domicilioValor.setZona(Util.getValue(valores, Numero.UNO.valueI));
				domicilioValor.setValor((BigDecimal) valores[Numero.DOS.valueI]);
				domicilioValor.setEstado(Util.getValue(valores, Numero.TRES.valueI));
				domicilioValor.setIdLocalidad(Long.valueOf(Util.getValue(valores, Numero.CUATRO.valueI)));
				domicilioValor.setLocalidad(Util.getValue(valores, Numero.CINCO.valueI));
				domicilioValor.setIdCiudad(Long.valueOf(Util.getValue(valores, Numero.SEIS.valueI)));
				domicilioValor.setCiudad(Util.getValue(valores, Numero.SIETE.valueI));
				domicilioValor.setIdDepartamento(Long.valueOf(Util.getValue(valores, Numero.OCHO.valueI)));
				domicilioValor.setDepartamento(Util.getValue(valores, Numero.NUEVE.valueI));
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
	public void crearValor(DomicilioValorDTO valor) throws Exception {
	}

	/**
	 * Servicio que permite editar un VALOR para los domicilios
	 * @param valor, DTO que contiene los datos del VALOR a editar
	 */
	@Transactional
	public void editarValor(DomicilioValorDTO valor) throws Exception {
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
}
