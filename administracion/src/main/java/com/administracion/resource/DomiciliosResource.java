package com.administracion.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.administracion.dto.domicilios.DomicilioValorDTO;
import com.administracion.dto.solicitudes.FiltroBusquedaDTO;
import com.administracion.service.DomiciliosService;
import com.administracion.util.BusinessException;
import com.administracion.util.Util;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * API que contiene los procesos de negocio para los domicilios
 */
@RestController
@RequestMapping("/domicilios")
public class DomiciliosResource {

	/** Service que contiene los procesos de negocio para domicilios */
	@Autowired
	private DomiciliosService service;

	/**
	 * Servicio que soporta el proceso de negocio para obtener los VALORES
	 * de los domicilios parametrizados en el sistema
	 *
	 * @param filtro, DTO que contiene los valores del filtro de busqueda
	 * @return DTO con la lista de valores domicilios parametrizados en el sistema
	 */
	@PostMapping(path = "/getValores",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Consultar valores domicilio del sistema", notes = "Obtiene los valores domicilios del sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error")})
	public ResponseEntity<Object> getValores(@RequestBody FiltroBusquedaDTO filtro) {
		try {
			return Util.getResponseSuccessful(this.service.getValores(filtro));
		} catch (Exception e) {
			return Util.getResponseError(DomiciliosResource.class.getSimpleName() + ".getValores ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite crear un VALOR para los domicilios
	 * @param valor, DTO que contiene los datos del VALOR a crear
	 */
	@PostMapping(path = "/crearValor",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Crear valor domicilios", notes = "Operación para la creación del valor domicilios")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error")})
	public ResponseEntity<Object> crearValor(@RequestBody DomicilioValorDTO valor) {
		try {
			// se procede a crear el VALOR
			this.service.crearValor(valor);

			// si llega a este punto es porque la creacion se ejecuto sin problemas
			return Util.getResponseOk();
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(DomiciliosResource.class.getSimpleName() + ".crearValor ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite editar un VALOR para los domicilios
	 * @param valor, DTO que contiene los datos del VALOR a editar
	 */
	@PostMapping(path = "/editarValor",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Editar valor domicilios", notes = "Operación para la edición del valor domicilios")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error")})
	public ResponseEntity<Object> editarValor(@RequestBody DomicilioValorDTO valor) {
		try {
			// se procede a editar el VALOR
			this.service.editarValor(valor);

			// si llega a este punto es porque la edicion se ejecuto sin problemas
			return Util.getResponseOk();
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(DomiciliosResource.class.getSimpleName() + ".editarValor ", e.getMessage());
		}
	}
}
