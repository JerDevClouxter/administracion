package com.administracion.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.administracion.dto.multinivel.CuentasProductosDTO;
import com.administracion.dto.multinivel.EmpresasProductosComisionesDTO;
import com.administracion.dto.multinivel.EmpresasProductosDTO;
import com.administracion.service.MultinivelService;
import com.administracion.util.BusinessException;
import com.administracion.util.Util;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/configurarRedDistribucion")
public class ConfigurarEmpresasResource {

	@Autowired
	private MultinivelService multinivelService;
	
	/**
	 * Metodo encargado de consultar las empresas padre por idUsuario
	 * 
	 * @param idUSuario
	 * @return List<EmpresasDTO>
	 * @throws BusinessException
	 */
	@GetMapping(path = "/consultarEmpresasPadreIdUsuario/{idUSuario}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Consultar Empresas padre por idUSuario", notes = "Operación para consular empresas padre asociadas al usaurio")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Object> consultarEmpresasPadreIdUsuario(@PathVariable Long idUSuario) {
		try {
			return Util.getResponseSuccessful(this.multinivelService.consultarEmpresasPadreIdUsuario(idUSuario));
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(MultinivelResource.class.getSimpleName() + ".consultarEmpresasPadreIdUsuario", e.getMessage());
		}
	}
	
	/**
	 * metodo encargado de buscar los productos existentes en el sistema
	 * 
	 * @return List<ProductosDTO>
	 * @throws BusinessException
	 */
	@GetMapping(path = "/consultarProductos", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Consultar productos", notes = "Operación para consular los productos existentes en el sistema")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Object> consultarProductos() {
		try {
			return Util.getResponseSuccessful(this.multinivelService.consultarProductos());
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(MultinivelResource.class.getSimpleName() + ".consultarProductos", e.getMessage());
		}
	}
	
	/**
	 * metodo encargado de buscar las comisiones existentes en el sistema
	 * 
	 * @return List<ComisionesDTO>
	 * @throws BusinessException
	 */
	@GetMapping(path = "/consultarComisiones", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Consultar comisiones", notes = "Operación para consular las comisiones existentes en el sistema")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Object> consultarComisiones() {
		try {
			return Util.getResponseSuccessful(this.multinivelService.consultarComisiones());
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(MultinivelResource.class.getSimpleName() + ".consultarComisiones", e.getMessage());
		}
	}
	
	/**
	 * metodo encargado de buscar las cuentas existentes en el sistema
	 * 
	 * @return List<CuentasDTO>
	 * @throws BusinessException
	 */
	@GetMapping(path = "/consultarCuentas", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Consultar cuentas", notes = "Operación para consular las cuentas existentes en el sistema")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Object> consultarCuentas() {
		try {
			return Util.getResponseSuccessful(this.multinivelService.consultarCuentas());
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(MultinivelResource.class.getSimpleName() + ".consultarCuentas", e.getMessage());
		}
	}
	
	/**
	 * Metodo encargado de almacenar los productos relacionados a una empresa
	 * @param productosEmpresa
	 */
	@PostMapping(path = "/insertarEmpresaProducto",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Almacena la configuración de productos para una empresa", notes = "Operación para Almacena la configuración de productos para una empresa")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Object> insertarEmpresaProducto(@RequestBody EmpresasProductosDTO productosEmpresa) {
		try {
			this.multinivelService.insertarEmpresaProducto(productosEmpresa);
			return Util.getResponseOk();
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(MultinivelResource.class.getSimpleName() + ".consultarCuentasProductosEmpresa ", e.getMessage());
		}
	}
	
	/**
	 * Metodo encargado de almacenar las comisiones asociadas  a los productos 
	 * relacionados a una empresa
	 * @param comisionesEmpPro
	 */
	@PostMapping(path = "/insertarEmpresaProductoComisiones",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Almacena la configuración de comisones de productos para una empresa", notes = "Operación para Almacena la configuración de comisiones de productos para una empresa")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Object> insertarEmpresaProductoComisiones(@RequestBody EmpresasProductosComisionesDTO comisionesEmpPro) {
		try {
			this.multinivelService.insertarEmpresaProductoComisiones(comisionesEmpPro);
			return Util.getResponseOk();
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(MultinivelResource.class.getSimpleName() + ".consultarCuentasProductosEmpresa ", e.getMessage());
		}
	}
	
	/**
	 * Metodo encargado de almacenar las cuentas asociadas  a los productos 
	 * relacionados a una empresa
	 * @param comisionesEmpPro
	 */
	@PostMapping(path = "/insertarCuentaProducto",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Almacena la configuración de cuentas de productos para una empresa", notes = "Operación para Almacena la configuración de cuentas de productos para una empresa")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Object> insertarCuentaProducto(@RequestBody CuentasProductosDTO cuentaProducto) {
		try {
			this.multinivelService.insertarCuentaProducto(cuentaProducto);
			return Util.getResponseOk();
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(MultinivelResource.class.getSimpleName() + ".insertarCuentaProducto ", e.getMessage());
		}
	}
	
}
