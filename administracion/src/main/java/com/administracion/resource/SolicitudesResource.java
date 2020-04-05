package com.administracion.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.administracion.dto.solicitudes.FiltroBusquedaDTO;
import com.administracion.service.SolicitudesService;
import com.administracion.util.Util;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * API que contiene los procesos de negocio para la administracion de las solicitudes
 */
@RestController
@RequestMapping("/solicitudes")
public class SolicitudesResource {

	/** Service que contiene los procesos de negocio para las solicitudes */
	@Autowired
	private SolicitudesService service;

	/**
	 * Servicio que soporta el proceso de negocio para obtener las solicitudes
	 * pendientes para los calendarios de los sorteos
	 *
	 * @param filtro, DTO que contiene los valores del filtro de busqueda
	 * @return DTO con la lista de solicitudes para los calendarios de los sorteos
	 */
	@PostMapping(path = "/getSolicitudesCalendarioSorteos",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Consultar solicitudes calendario sorteo", notes = "Obtiene las solicitudes para los calendarios sorteos")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error")})
	public ResponseEntity<Object> getSolicitudesCalendarioSorteos(@RequestBody FiltroBusquedaDTO filtro) {
		try {
			return Util.getResponseSuccessful(this.service.getSolicitudesCalendarioSorteos(filtro));
		} catch (Exception e) {
			return Util.getResponseError(SolicitudesResource.class.getSimpleName() + ".getSolicitudesCalendarioSorteos ", e.getMessage());
		}
	}
}
