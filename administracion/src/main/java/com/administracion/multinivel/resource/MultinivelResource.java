package com.administracion.multinivel.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.administracion.dto.multinivel.DatosEmpresaProductoConfiguracionDTO;
import com.administracion.multinivel.service.MultinivelService;
import com.administracion.util.BusinessException;
import com.administracion.util.Util;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Servicio que contiene todos los procesos de negocio para las funcionalidades
 * relacionadas con multinivel localhost:puerto/path_name/
 */
@RestController
@RequestMapping("/multinivel")
public class MultinivelResource {

	@Autowired
	private MultinivelService multinivelService;

	/**
	 * Metodo encargado de consultar las empresas por idUsuario
	 * 
	 * @param idUSuario
	 * @return List<EmpresasIdUsuarioDTO>
	 * @throws BusinessException
	 */
	@GetMapping(path = "/consultarEmpresasIdUsuario/{idUSuario}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Consultar Empresas por idUSuario", notes = "Operación para consular empresas asociadas al usaurio")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Object> consultarEmpresasIdUsuario(@PathVariable Long idUSuario) {
		try {
			return Util.getResponseSuccessful(this.multinivelService.consultarEmpresasIdUsuario(idUSuario));
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(MultinivelResource.class.getSimpleName() + ".consultarEmpresasIdUsuario", e.getMessage());
		}
	}

	/**
	 * metodo encargado de buscar los productos asociados a una empresa
	 * 
	 * @param idEmpresa
	 * @return List<EmpresasProductosDTO>
	 * @throws BusinessException
	 */
	@GetMapping(path = "/consultarProductosIdEmpresa", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Consultar productos por idEmpresa", notes = "Operación para consular productos asociados a una empresa")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Object> consultarProductosIdEmpresa(@RequestParam("idEmpresa") Long idEmpresa, @RequestParam("esEditarConfiguracion") Boolean esEditarConfiguracion) {
		try {
			return Util.getResponseSuccessful(this.multinivelService.consultarProductosIdEmpresa(idEmpresa, esEditarConfiguracion));
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(MultinivelResource.class.getSimpleName() + ".consultarProductosIdEmpresa", e.getMessage());
		}
	}

	/**
	 * metodo encargado de buscar las comisiones asociadas a los productos asociados
	 * a una empresa
	 * 
	 * @param idEmpresa
	 * @param idProducto
	 * @return List<EmpresasProductosDTO>
	 * @throws BusinessException
	 */
	@GetMapping(path = "/consultarEmpresaProductosComision", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Consultar comisión productos empresa", notes = "Operación para consular la comisión de los productos asociados a una empresa")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Object> consultarEmpresaProductosComision(@RequestParam("idEmpresa") Long idEmpresa,
			@RequestParam("idProducto") Long idProducto) {
		try {
			return Util.getResponseSuccessful(
					this.multinivelService.consultarEmpresaProductosComision(idEmpresa, idProducto));
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(MultinivelResource.class.getSimpleName() + ".consultarEmpresaProductosComision", e.getMessage());
		}
	}
	
	/**
	 * metodo encargado de buscar las cuentas contables asociadas a los productos asociados
	 * a una empresa
	 * 
	 * @param idEmpresa
	 * @param idProducto
	 * @return List<CuentasProductosDTO>
	 * @throws BusinessException
	 */
	@GetMapping(path = "/consultarCuentasProductosEmpresa", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Consultar cuentas productos empresa", notes = "Operación para consular las cuentas asociadas a los productos asociados a una empresa")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Object> consultarCuentasProductosEmpresa(@RequestParam("idEmpresa") Long idEmpresa,
			@RequestParam("idProducto") Long idProducto) {
		try {
			return Util.getResponseSuccessful(
					this.multinivelService.consultarCuentasProductosEmpresa(idEmpresa, idProducto));
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(MultinivelResource.class.getSimpleName() + ".consultarCuentasProductosEmpresa", e.getMessage());
		}
	}
	@PostMapping(path = "/asociarConfigProductosEmpresas",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Almacena la asociación de productos para una empresa", notes = "Operación para Almacena la asociación de productos para una empresa")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Object> asociarConfigProductosEmpresas(@RequestBody DatosEmpresaProductoConfiguracionDTO productosEmpresaConf) {
		try {
			productosEmpresaConf.toString();
			return Util.getResponseSuccessful(this.multinivelService.asociarConfigProductosEmpresas(productosEmpresaConf));
		} catch (Exception e) {
			return Util.getResponseError(MultinivelResource.class.getSimpleName() + ".consultarCuentasProductosEmpresa ", e.getMessage());
		}
	}
}
