package com.administracion.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.administracion.service.ConfiguracionService;
import com.administracion.service.MultinivelService;
import com.administracion.util.BusinessException;
import com.administracion.util.Util;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/configuracion")
public class ConfiguracionResource {
	
	
	@Autowired
	private ConfiguracionService configuracionService;
	
	/**
	 * metodo encargado de un usuario por tipo u numero de documento
	 * 
	 * @return ConfiguracionUsuarioDTO información de un usuario registrado en el sistema
	 * @throws BusinessException
	 */
	@GetMapping(path = "/consultarUsuarioTipDocNum", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Consultar usuario tipo documento", notes = "Operación para consular  en el sistema un usuario existente, por medio de el tipo y numero de documento")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Object> consultarUsuarioTipDocNum(@RequestParam("tipoDocumento") String tipoDocumento, @RequestParam("numeroDocumento") Long numeroDocumento) {
		try {
			return Util.getResponseSuccessful(this.configuracionService.consultarUsuarioTipDocNum(tipoDocumento, numeroDocumento));
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(MultinivelResource.class.getSimpleName() + ".consultarUsuarioTipDocNum", e.getMessage());
		}
	}
}
