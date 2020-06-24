package com.administracion.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.administracion.dto.configurar.ConfiguracionUsuarioDTO;
import com.administracion.dto.configurar.UsuariosDTO;
import com.administracion.dto.configurar.UsuariosRolesEmpresasDTO;
import com.administracion.dto.solicitudes.FiltroBusquedaDTO;
import com.administracion.service.ConfiguracionService;
import com.administracion.util.BusinessException;
import com.administracion.util.Util;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/configuracionUsuario")
public class ConfiguracionResource {

	@Autowired
	private ConfiguracionService configuracionService;

	/**
	 * metodo encargado de buscar un usuario por tipo o numero de documento
	 * 
	 * @return ConfiguracionUsuarioDTO información de usuarios registrado en el
	 *         sistema
	 * @throws BusinessException
	 */
	@PostMapping(path = "/consultarUsuarioTipDocNum", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Consultar usuario tipo documento", notes = "Operación para consular en el sistema los usuarios existentes")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Object> consultarUsuarioTipDocNum(@RequestBody FiltroBusquedaDTO filtro) {
		try {
			return Util.getResponseSuccessful(
					this.configuracionService.consultarUsuarioTipDocNum(filtro));
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionResource.class.getSimpleName() + ".consultarUsuarioTipDocNum",
					e.getMessage());
		}
	}

	/**
	 * Metodo encargado de crear o editar la información de un usuario
	 * 
	 * @param configuracionUsuario
	 */
	@PostMapping(path = "/crearEditarUsuario", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Almacena o edita el usuario", notes = "Operación para crear o editar un usuario en el sistema")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Object> crearEditarUsuario(@RequestBody ConfiguracionUsuarioDTO configuracionUsuario) {
		try {
			return Util.getResponseSuccessful(this.configuracionService.crearEditarUsuario(configuracionUsuario));
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionResource.class.getSimpleName() + ".crearEditarUsuario ",
					e.getMessage());
		}
	}

	/**
	 * Metodo encargado de consultar las empresas y roles asociadas a un usuario
	 * 
	 * @return Inforamción de las empresas y roles asociadas al usuario
	 * @throws BusinessException
	 */
	@GetMapping(path = "/consultarUsuarioRolesIdUsuario", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Consultar Roles y Empresas Asociadas a un IdUsuario", notes = "Operación para consular en el sistema las empresas y toles asociadas a un usuario")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Object> consultarUsuarioRolesIdUsuario(@RequestParam("idUsuario") String idUsuario) {
		try {
			return Util
					.getResponseSuccessful(this.configuracionService.consultarEmpresasRoles(Long.valueOf(idUsuario)));

		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(
					ConfiguracionResource.class.getSimpleName() + ".consultarUsuarioRolesIdUsuario", e.getMessage());
		}
	}

	/**
	 * Metodo encargado de crear o editar la información de un usuario
	 * 
	 * @param configuracionUsuario
	 */
	@PutMapping(path = "/actContrasPrimerVez", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Almacena o edita el usuario", notes = "Operación para crear o editar un usuario en el sistema")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Object> actContrasPrimerVez(@RequestBody UsuariosDTO usuarioDTO) {
		try {
			this.configuracionService.actContrasPrimerVez(usuarioDTO.getIdUsuario(), usuarioDTO.getClave());
			return Util.getResponseOk();
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(ConfiguracionResource.class.getSimpleName() + ".crearEditarUsuario ",
					e.getMessage());
		}
	}
}
