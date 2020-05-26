package com.administracion.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.administracion.dto.recursos.RecursoDTO;
import com.administracion.dto.solicitudes.FiltroBusquedaDTO;
import com.administracion.service.RecursosService;
import com.administracion.util.BusinessException;
import com.administracion.util.Util;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * API que contiene los procesos de negocio para la administracion de los RECURSOS
 */
@RestController
@RequestMapping("/recursos")
public class RecursosResource {

	/** Service que contiene los procesos de negocio para la admin de RECURSOS */
	@Autowired
	private RecursosService service;

	/**
	 * Servicio que soporta el proceso de negocio para obtener los RECURSOS
	 * parametrizados en el sistema
	 *
	 * @param filtro, DTO que contiene los valores del filtro de busqueda
	 * @return DTO con la lista de RECURSOS parametrizados en el sistema
	 */
	@PostMapping(path = "/getRecursos",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Consultar recursos del sistema", notes = "Obtiene los recursos del sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error")})
	public ResponseEntity<Object> getRecursos(@RequestBody FiltroBusquedaDTO filtro) {
		try {
			return Util.getResponseSuccessful(this.service.getRecursos(filtro));
		} catch (Exception e) {
			return Util.getResponseError(RecursosResource.class.getSimpleName() + ".getRecursos ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite crear un RECURSO en el sistema
	 * @param recurso, DTO que contiene los datos del RECURSO a crear
	 */
	@PostMapping(path = "/crearRecurso",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Crear Recurso", notes = "Operación para la creación del Recurso")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error")})
	public ResponseEntity<Object> crearRecurso(@RequestBody RecursoDTO recurso) {
		try {
			// se procede a crear el RECURSO
			this.service.crearRecurso(recurso);

			// si llega a este punto es porque la creacion se ejecuto sin problemas
			return Util.getResponseOk();
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(RecursosResource.class.getSimpleName() + ".crearRecurso ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite editar un RECURSO en el sistema
	 * @param recurso, DTO que contiene los datos del RECURSO a editar
	 */
	@PostMapping(path = "/editarRecurso",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Editar Recurso", notes = "Operación para la edición del Recurso")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error")})
	public ResponseEntity<Object> editarRecurso(@RequestBody RecursoDTO recurso) {
		try {
			// se procede a editar el RECURSO
			this.service.editarRecurso(recurso);

			// si llega a este punto es porque la edicion se ejecuto sin problemas
			return Util.getResponseOk();
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(RecursosResource.class.getSimpleName() + ".editarRecurso ", e.getMessage());
		}
	}
}
