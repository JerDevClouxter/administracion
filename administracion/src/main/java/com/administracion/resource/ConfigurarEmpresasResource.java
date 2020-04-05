package com.administracion.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.administracion.dto.multinivel.CuentasProductosDTO;
import com.administracion.dto.multinivel.DatosEmpresaProductoConfiguracionDTO;
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
			return Util.getResponseError(MultinivelResource.class.getSimpleName() + ".insertarEmpresaProducto ", e.getMessage());
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
			return Util.getResponseError(MultinivelResource.class.getSimpleName() + ".insertarEmpresaProductoComisiones ", e.getMessage());
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
	
	/**
	 * metodo encargado de buscar los productos asociados a una empresa padre
	 * 
	 * @param idEmpresa
	 * @return List<EmpresasProductosDTO>
	 * @throws BusinessException
	 */
	@GetMapping(path = "/consultarProductosEditarIdEmpresa/{idEmpresa}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Consultar productos por idEmpresa padre", notes = "Operación para consular productos asociados a una empresa padre")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Object> consultarProductosEditarIdEmpresa(@PathVariable Long idEmpresa) {
		try {
			return Util.getResponseSuccessful(this.multinivelService.consultarProductosEmpresa(idEmpresa, false));
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(MultinivelResource.class.getSimpleName() + ".consultarProductosEditarIdEmpresa", e.getMessage());
		}
	}
	
	
	/**
	 * metodo encargado de buscar las comisiones asociadas a un producto para una empresa padre
	 * 
	 * @param idEmpresa
	 * @return List<EmpresasProductosDTO>
	 * @throws BusinessException
	 */
	@GetMapping(path = "/consultarComisionProdEditarEmpresa/{idEmpresa}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Consultar comisiones productos por idEmpresa padre", notes = "Operación para consular comisiones productos asociados a una empresa padre")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Object> consultarComisionProdEditarEmpresa(@PathVariable Long idEmpresa) {
		try {
			return Util.getResponseSuccessful(this.multinivelService.consultarComisionProdEditarEmpresa(idEmpresa));
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(MultinivelResource.class.getSimpleName() + ".consultarComisionProdEditarEmpresa", e.getMessage());
		}
	}
	
	@GetMapping(path = "/consultarCuentasEditarEmpresa/{idEmpresa}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Consultar cuentas productos por idEmpresa padre", notes = "Operación para consular cuentas productos asociados a una empresa padre")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Object> consultarCuentasEditarEmpresa(@PathVariable Long idEmpresa) {
		try {
			return Util.getResponseSuccessful(this.multinivelService.consultarCuentasEditarEmpresa(idEmpresa));
		} catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(MultinivelResource.class.getSimpleName() + ".consultarCuentasEditarEmpresa", e.getMessage());
		}
	}
	
	@PutMapping(path = "/editarConfigProductosEmpresas",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Edita la asociación de productos para una empresa", notes = "Operación para editar la asociación de productos para una empresa")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Object> editarConfigProductosEmpresas(@RequestBody DatosEmpresaProductoConfiguracionDTO editarProductosEmpresaConf) {
		try {
			this.multinivelService.editarProductosConfEmpPadre(editarProductosEmpresaConf);
			return Util.getResponseOk();
		}catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(MultinivelResource.class.getSimpleName() + ".editarConfigProductosEmpresas ", e.getMessage());
		}
	}
	
	@PutMapping(path = "/editarConfigComisionesProdEmpresas",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Edita la asociación de comisiones productos para una empresa", notes = "Operación para editar la asociación de comisiones productos para una empresa")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Object> editarConfigComisionesProdEmpresas(@RequestBody DatosEmpresaProductoConfiguracionDTO editarProdComisionesEmpresaConf) {
		try {
			this.multinivelService.editarComProdConfEmpPadre(editarProdComisionesEmpresaConf);
			return Util.getResponseOk();
		}catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(MultinivelResource.class.getSimpleName() + ".editarConfigComisionesProdEmpresas ", e.getMessage());
		}
	}
	
	@PutMapping(path = "/editarConfigCuentasProdEmpresas",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Editar la asociación de cuentas productos para una empresa", notes = "Operación para editar de la asociación de cuentas productos para una empresa")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Proceso ejecutado satisfactoriamente"),
			@ApiResponse(code = 400, message = "Se presentó una exception de negocio"),
			@ApiResponse(code = 404, message = "Recurso no encontrado"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<Object> editarConfigCuentasProdEmpresas(@RequestBody DatosEmpresaProductoConfiguracionDTO editarProdCuentasEmpresaConf) {
		try {
			this.multinivelService.editarCuenProdConfEmpPadre(editarProdCuentasEmpresaConf);
			return Util.getResponseOk();
		}catch (BusinessException e) {
			return Util.getResponseBadRequest(e.getMessage());
		} catch (Exception e) {
			return Util.getResponseError(MultinivelResource.class.getSimpleName() + ".editarConfigCuentasProdEmpresas ", e.getMessage());
		}
	}
}
