package com.administracion.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.administracion.service.EmpresasService;
import com.administracion.util.BusinessException;
import com.administracion.util.Util;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Servicio que contiene todos los procesos de negocio para las Empresas
 * localhost:puerto/path_name/
 */
@RestController
@RequestMapping("/empresas")
public class EmpresaResource {

	@Autowired
	private EmpresasService empresaService;

	@GetMapping(path = "/empresas/{idEmpresa}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Consultar Empresa", notes = "Operación para consular empresa usaurio")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Object> consultarEmpresa(@PathVariable Long idEmpresa) {
		try {
			return Util.getResponseSuccessful(this.empresaService.findByIdEmpresa(idEmpresa));
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(EmpresaResource.class.getSimpleName() + ".consultarEmpresa", e.getMessage());
		}
	}
	
	@GetMapping(path = "/consultarProductosEmpresas/{idEmpresa}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Consultar Empresa", notes = "Operación para consular empresa usaurio")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Object> consultarProductosEmpresas(@PathVariable Long idEmpresa) {
		try {
			return Util.getResponseSuccessful(this.empresaService.consultarProductosEmpresas(idEmpresa));
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(EmpresaResource.class.getSimpleName() + ".consultarEmpresa", e.getMessage());
		}
	}
	
	
	@GetMapping(path = "/consultarEmpresas", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Consultar Loterias", notes = "Operación para consular empresas")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Object> consultarEmpresas() {
		try {
			return Util.getResponseSuccessful(this.empresaService.consultarEmpresas());
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(EmpresaResource.class.getSimpleName() + ".consultarLoteria", e.getMessage());
		}
	}


}
