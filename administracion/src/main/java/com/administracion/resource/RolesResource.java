package com.administracion.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.administracion.dto.solicitudes.FiltroBusquedaDTO;
import com.administracion.service.RolesService;
import com.administracion.util.Util;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * API que contiene los procesos de negocio para la administracion de los ROLES
 */
@RestController
@RequestMapping("/roles")
public class RolesResource {

	/** Service que contiene los procesos de negocio para la admin de roles */
	@Autowired
	private RolesService service;

	/**
	 * Servicio que soporta el proceso de negocio para obtener los ROLES
	 * parametrizados en el sistema
	 *
	 * @param filtro, DTO que contiene los valores del filtro de busqueda
	 * @return DTO con la lista de roles parametrizados en el sistema
	 */
	@PostMapping(path = "/getRoles",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Consultar roles del sistema", notes = "Obtiene los roles del sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error")})
	public ResponseEntity<Object> getRoles(@RequestBody FiltroBusquedaDTO filtro) {
		try {
			return Util.getResponseSuccessful(this.service.getRoles(filtro));
		} catch (Exception e) {
			return Util.getResponseError(RolesResource.class.getSimpleName() + ".getRoles ", e.getMessage());
		}
	}
}
