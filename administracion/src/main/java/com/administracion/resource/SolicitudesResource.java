package com.administracion.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.administracion.dto.solicitudes.DetalleSolicitudCalendarioSorteoDTO;
import com.administracion.dto.solicitudes.FiltroBusquedaDTO;
import com.administracion.dto.solicitudes.SolicitudCalendarioSorteoDTO;
import com.administracion.service.SolicitudesService;
import com.administracion.util.BusinessException;
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

	/**
	 * Servicio que permite consultar el detalle de la solicitud para calendario sorteos
	 *
	 * @param solicitud, DTO que contiene los datos de la solicitud
	 * @return DTO con los datos del detalle de la solicitud para calendario sorteos
	 */
	@PostMapping(path = "/getDetalleSolicitudCalendarioSorteos",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Consultar detalle solicitud calendario sorteo", notes = "Obtiene el detalle de la solicitud para los calendarios sorteos")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error")})
	public ResponseEntity<Object> getDetalleSolicitudCalendarioSorteos(@RequestBody SolicitudCalendarioSorteoDTO solicitud) {
		try {
			return Util.getResponseSuccessful(this.service.getDetalleSolicitudCalendarioSorteos(solicitud));
		} catch (Exception e) {
			return Util.getResponseError(SolicitudesResource.class.getSimpleName() + ".getDetalleSolicitudCalendarioSorteos ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite rechazar una solicitud de calendario sorteos
	 * @param solicitud, DTO que contiene los datos de la solicitud a rechazar
	 */
	@PostMapping(path = "/rechazarSolicitudCalendarioSorteos",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Rechazar solicitud calendario sorteos", notes = "Permite rechazar una solicitud de calendario sorteos")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error")})
	public ResponseEntity<Object> rechazarSolicitudCalendarioSorteos(@RequestBody DetalleSolicitudCalendarioSorteoDTO solicitud) {
		try {
			// se procede a rechazar la solicitud
			this.service.rechazarSolicitudCalendarioSorteos(solicitud);

			// si llega a este punto es porque el proceso se ejecuto sin problemas
			return Util.getResponseOk();
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(SolicitudesResource.class.getSimpleName() + ".rechazarSolicitudCalendarioSorteos ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite autorizar una solicitud de calendario sorteos
	 * @param solicitud, DTO que contiene los datos de la solicitud autorizar
	 */
	@PostMapping(path = "/autorizarSolicitudCalendarioSorteos",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Autorizar solicitud calendario sorteos", notes = "Permite autorizar una solicitud de calendario sorteos")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error")})
	public ResponseEntity<Object> autorizarSolicitudCalendarioSorteos(@RequestBody DetalleSolicitudCalendarioSorteoDTO solicitud) {
		try {
			// se procede autorizar la solicitud
			this.service.autorizarSolicitudCalendarioSorteos(solicitud);

			// si llega a este punto es porque el proceso se ejecuto sin problemas
			return Util.getResponseOk();
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(SolicitudesResource.class.getSimpleName() + ".autorizarSolicitudCalendarioSorteos ", e.getMessage());
		}
	}
}
