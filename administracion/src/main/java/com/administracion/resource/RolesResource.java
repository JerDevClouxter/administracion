package com.administracion.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.administracion.dto.roles.RolDTO;
import com.administracion.dto.solicitudes.FiltroBusquedaDTO;
import com.administracion.service.RolesService;
import com.administracion.util.BusinessException;
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

	/**
	 * Servicio que permite crear un ROL en el sistema
	 * @param rol, DTO que contiene los datos del ROL a crear
	 */
	@PostMapping(path = "/crearRol",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Crear Rol", notes = "Operación para la creación del Rol")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error")})
	public ResponseEntity<Object> crearRol(@RequestBody RolDTO rol) {
		try {
			// se procede a crear el ROL
			this.service.crearRol(rol);

			// si llega a este punto es porque la creacion se ejecuto sin problemas
			return Util.getResponseOk();
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(RolesResource.class.getSimpleName() + ".crearRol ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite obtener los detalles de un ROL
	 *
	 * @param idRol, identificador del ROL
	 * @return, datos con todos los detalles del ROL
	 */
	@GetMapping(path = "/getDetalleRol",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Obtener detalle ROL", notes = "Permite obtener los detalles de un ROL")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error")})
	public ResponseEntity<Object> getDetalleRol(@RequestParam Long idRol) {
		try {
			return Util.getResponseSuccessful(this.service.getDetalleRol(idRol));
		} catch (Exception e) {
			return Util.getResponseError(RolesResource.class.getSimpleName() + ".getDetalleRol ", e.getMessage());
		}
	}

	/**
	 * Servicio que permite soportar el proceso de negocio para la edicion del ROL
	 * @param rol, DTO que contiene los datos del ROL a modificar
	 */
	@PostMapping(path = "/editarRol",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Editar Rol", notes = "Operación para la edición del Rol")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error")})
	public ResponseEntity<Object> editarRol(@RequestBody RolDTO rol) {
		try {
			// se procede a editar el ROL
			this.service.editarRol(rol);

			// si llega a este punto es porque la edicion se ejecuto sin problemas
			return Util.getResponseOk();
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(RolesResource.class.getSimpleName() + ".editarRol ", e.getMessage());
		}
	}
}
