package com.administracion.multinivel.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.administracion.builder.Builder;
import com.administracion.constant.MessagesBussinesKey;
import com.administracion.constant.Numero;
import com.administracion.constant.SQLConstant;
import com.administracion.dto.EmpresasDTO;
import com.administracion.dto.multinivel.ChildrenDTO;
import com.administracion.dto.multinivel.ComisionesDTO;
import com.administracion.dto.multinivel.CuentasDTO;
import com.administracion.dto.multinivel.CuentasProductosDTO;
import com.administracion.dto.multinivel.DatosEmpresaProductoConfiguracionDTO;
import com.administracion.dto.multinivel.EmpresasIdUsuarioDTO;
import com.administracion.dto.multinivel.EmpresasProductosComisionesDTO;
import com.administracion.dto.multinivel.EmpresasProductosDTO;
import com.administracion.dto.multinivel.ProductosDTO;
import com.administracion.entity.Comisiones;
import com.administracion.entity.Cuentas;
import com.administracion.entity.Productos;
import com.administracion.repository.IComisionesRepository;
import com.administracion.repository.ICuentasRepository;
import com.administracion.repository.IEmpresasRepository;
import com.administracion.repository.IProductosRepository;
import com.administracion.util.BusinessException;
import com.administracion.util.Util;

/**
 * Service que contiene los procesos de negocio para multinivel
 */
@Service
@Transactional
public class MultinivelService {

	/** Contexto de la persistencia del sistema */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Repository que contiene los metodos utilitarios para la persistencia de la
	 * entidad EMPRESAS
	 */
	@Autowired
	private IEmpresasRepository empresaRepository;

	
	/**
	 * Repository que contiene los metodos utilitarios para la persistencia de la
	 * entidad PRODUCTOS
	 */
	@Autowired
	private IProductosRepository productosRepository;
	
	/**
	 * Repository que contiene los metodos utilitarios para la persistencia de la
	 * entidad COMISIONES
	 */
	@Autowired
	private IComisionesRepository comisionesRepository;
	
	/**
	 * Repository que contiene los metodos utilitarios para la persistencia de la
	 * entidad CUENTAS
	 */
	@Autowired
	private ICuentasRepository cuentasRepository;

	/**
	 * metodo encargado de buscar los productos existentes en el sistema
	 * 
	 * @return List<ProductosDTO>
	 * @throws BusinessException
	 */
	public List<ProductosDTO> consultarProductos() throws BusinessException {
		List<ProductosDTO> listProductosDTO = null;
		Builder<Productos, ProductosDTO> builder = new Builder<>(ProductosDTO.class);
		List<Productos> listProductos = this.productosRepository.findAll();
		if (!listProductos.isEmpty()) {
			listProductosDTO = builder.copy(listProductos);
		}
		return listProductosDTO;
	}
	
	/**
	 * metodo encargado de buscar las comisiones existentes en el sistema
	 * 
	 * @return List<ComisionesDTO>
	 * @throws BusinessException
	 */
	public List<ComisionesDTO> consultarComisiones() throws BusinessException {
		List<ComisionesDTO> listComisionesDTO = null;
		Builder<Comisiones, ComisionesDTO> builder = new Builder<>(ComisionesDTO.class);
		List<Comisiones> listComisiones = this.comisionesRepository.findAll();
		if (!listComisiones.isEmpty()) {
			listComisionesDTO = builder.copy(listComisiones);
		}
		return listComisionesDTO;
	}
	
	/**
	 * metodo encargado de buscar las cuentas existentes en el sistema
	 * 
	 * @return List<CuentasDTO>
	 * @throws BusinessException
	 */
	public List<CuentasDTO> consultarCuentas() throws BusinessException {
		List<CuentasDTO> listCuentasDTO = null;
		Builder<Cuentas, CuentasDTO> builder = new Builder<>(CuentasDTO.class);
		List<Cuentas> listCuentas = this.cuentasRepository.findAll();
		if (!listCuentas.isEmpty()) {
			listCuentasDTO = builder.copy(listCuentas);
		}
		return listCuentasDTO;
	}
	
	/**
	 * Metodo encargado de consultar las empresas por idUsuario
	 * 
	 * @param idUSuario
	 * @return List<EmpresasIdUsuarioDTO>
	 * @throws BusinessException
	 */
	public List<EmpresasIdUsuarioDTO> consultarEmpresasIdUsuario(Long idUSuario) throws BusinessException {
		Query q = em.createNativeQuery(SQLConstant.SELECT_EMPRESAS_POR_ID_USAURIO).setParameter("idUSuario", idUSuario);
		List<Object[]> result = q.getResultList();
		EmpresasDTO empresaDTO = null;
		List<EmpresasIdUsuarioDTO> empIdUsuarioDTOList = new ArrayList<>();
		List<EmpresasDTO> empresaDTOList = new ArrayList<>();
		List<EmpresasDTO> empresaHijaDTOList = new ArrayList<>();
		if (result != null && !result.isEmpty()) {
			for (Object[] data : result) {
				if (data[Numero.SEIS.valueI] != null) {
					empresaDTO = new EmpresasDTO();

					empresaDTO.setIdEmpresa(Long.valueOf(Util.getValue(data, Numero.ZERO.valueI)));
					empresaDTO.setNitEmpresa(Util.getValue(data, Numero.UNO.valueI));
					empresaDTO.setRazonSocial(Util.getValue(data, Numero.DOS.valueI));
					empresaDTO.setIdEmpresaPadre(Long.valueOf(Util.getValue(data, Numero.SEIS.valueI)));
					empresaHijaDTOList.add(empresaDTO);
				} else {
					empresaDTO = new EmpresasDTO();
					empresaDTO.setIdEmpresa(Long.valueOf(Util.getValue(data, Numero.ZERO.valueI)));
					empresaDTO.setNitEmpresa(Util.getValue(data, Numero.UNO.valueI));
					empresaDTO.setRazonSocial(Util.getValue(data, Numero.DOS.valueI));
					empresaDTOList.add(empresaDTO);
				}

			}

			for (EmpresasDTO empresasDTO : empresaDTOList) {
				EmpresasIdUsuarioDTO empIdUsuDTO = new EmpresasIdUsuarioDTO();
				empIdUsuDTO.setData(empresasDTO);
				ChildrenDTO hija = new ChildrenDTO();
				for (EmpresasDTO empresaHijaDTO : empresaHijaDTOList) {
					List<ChildrenDTO> listHija = new ArrayList<>();
					if (empresaHijaDTO.getIdEmpresaPadre().equals(empresasDTO.getIdEmpresa())) {
						hija.setData(empresaHijaDTO);
						listHija.add(hija);
					}
					if (listHija != null && !listHija.isEmpty()) {
						empIdUsuDTO.setChildren(listHija);
					}

				}
				empIdUsuarioDTOList.add(empIdUsuDTO);
			}
		} else {
			throw new BusinessException(MessagesBussinesKey.KEY_SIN_RELACION_EMPRESA_POR_IDUSUARIO.value);
		}

		return empIdUsuarioDTOList;
	}

	/**
	 * metodo encargado de buscar los productos asociados a una empresa
	 * 
	 * @param idEmpresa
	 * @return List<EmpresasProductosDTO>
	 * @throws BusinessException
	 */
	public List<EmpresasProductosDTO> consultarProductosIdEmpresa(Long idEmpresa, Boolean esEditarConfiguracion)
			throws BusinessException {
		List<EmpresasProductosDTO> empProductosList = new ArrayList<>();
		Query q = em.createNativeQuery(SQLConstant.SELECT_PRODUCTOS_ID_EMPRESA).setParameter("idEmpresa", idEmpresa);
		List<Object[]> listaT = q.getResultList();
		if (listaT != null && !listaT.isEmpty()) {
			for (Object[] result : listaT) {
				EmpresasProductosDTO empProducto = new EmpresasProductosDTO();
				empProducto.setIdProducto(Long.valueOf(Util.getValue(result, Numero.ZERO.valueI)));
				empProducto.setNombreProducto(Util.getValue(result, Numero.UNO.valueI));
				empProducto.setValorMinimo(Util.getValue(result, Numero.DOS.valueI) != null
						? Double.valueOf(Util.getValue(result, Numero.DOS.valueI))
						: 0);
				empProducto.setValorMaximo(Util.getValue(result, Numero.TRES.valueI) != null
						? Double.valueOf(Util.getValue(result, Numero.TRES.valueI))
						: 0);
				empProducto.setValorMaximoDia(Util.getValue(result, Numero.CUATRO.valueI) != null
						? Double.valueOf(Util.getValue(result, Numero.CUATRO.valueI))
						: 0);
				empProducto.setHoraInicioVenta(
						Util.getValue(result, Numero.CINCO.valueI) != null ? Util.getValue(result, Numero.CINCO.valueI)
								: "");
				empProducto.setHoraFinalVenta(
						Util.getValue(result, Numero.SEIS.valueI) != null ? Util.getValue(result, Numero.SEIS.valueI)
								: "");
				empProducto.setIdEmpresa(Long.valueOf(Util.getValue(result, Numero.SIETE.valueI)));
				
				empProductosList.add(empProducto);

			}
		} else {
			throw new BusinessException(MessagesBussinesKey.KEY_SIN_ASOCIACION_PRODUCTOS_EMPRESA_SELECCIONADA.value);
		}

		return empProductosList;
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
	public List<EmpresasProductosComisionesDTO> consultarEmpresaProductosComision(Long idEmpresa, Long idProducto)
			throws BusinessException {
		List<EmpresasProductosComisionesDTO> empProdComisionesList = new ArrayList<>();
		Query q = em.createNativeQuery(SQLConstant.SELECT_EMPRESAS_PRODUCTOS_COMISIONES_ID_EMPRESA)
				.setParameter("idEmpresa", idEmpresa).setParameter("idProducto", idProducto);
		List<Object[]> listaT = q.getResultList();
		if (listaT != null && !listaT.isEmpty()) {
			for (Object[] result : listaT) {
				EmpresasProductosComisionesDTO empProCom = new EmpresasProductosComisionesDTO();
				empProCom.setIdEmpresa(Long.valueOf(Util.getValue(result, Numero.ZERO.valueI)));
				empProCom.setIdProducto(Long.valueOf(Util.getValue(result, Numero.UNO.valueI)));
				empProCom.setIdComision(Long.valueOf(Util.getValue(result, Numero.DOS.valueI)));
				empProCom.setPorcentajeComision(Util.getValue(result, Numero.TRES.valueI) != null
						? Double.valueOf(Util.getValue(result, Numero.TRES.valueI))
						: 0);
				empProCom.setValorFijoComision(Util.getValue(result, Numero.CUATRO.valueI) != null
						? Double.valueOf(Util.getValue(result, Numero.CUATRO.valueI))
						: 0);
				// OJO que va aqui
				// empProCom.setIdEmpProCom(Long.valueOf(Util.getValue(result,
				// Numero.CINCO.valueI)));
				empProCom.setNombreProducto(Util.getValue(result, Numero.SEIS.valueI));
				empProdComisionesList.add(empProCom);
			}
		} else {
			throw new BusinessException(MessagesBussinesKey.KEY_SIN_ASOCIACION_COMISION_PRODUCTOS_EMPRESA.value);
		}
		return empProdComisionesList;
	}

	/**
	 * metodo encargado de buscar las cuentas contables asociadas a los productos
	 * asociados a una empresa
	 * 
	 * @param idEmpresa
	 * @param idProducto
	 * @return List<CuentasProductosDTO>
	 * @throws BusinessException
	 */
	public List<CuentasProductosDTO> consultarCuentasProductosEmpresa(Long idEmpresa, Long idProducto)
			throws BusinessException {
		List<CuentasProductosDTO> empProdCueList = new ArrayList<>();
		Query q = em.createNativeQuery(SQLConstant.SELECT_CUENTAS_PRODUCTOS_EMPRESA)
				.setParameter("idEmpresa", idEmpresa).setParameter("idProducto", idProducto);
		List<Object[]> listaT = q.getResultList();
		if (listaT != null && !listaT.isEmpty()) {
			for (Object[] result : listaT) {
				CuentasProductosDTO empProCue = new CuentasProductosDTO();
				empProCue.setIdEmpresa(Long.valueOf(Util.getValue(result, Numero.ZERO.valueI)));
				empProCue.setIdProducto(Long.valueOf(Util.getValue(result, Numero.UNO.valueI)));
				empProCue.setIdCuenta(Long.valueOf(Util.getValue(result, Numero.DOS.valueI)));
				empProCue.setCodCuenta(
						Util.getValue(result, Numero.TRES.valueI) != null ? Util.getValue(result, Numero.TRES.valueI)
								: "");
				empProCue.setNombreCuenta(Util.getValue(result, Numero.CUATRO.valueI));
				empProCue.setCuentaAsociada(Util.getValue(result, Numero.CINCO.valueI) != null
						? Integer.valueOf(Util.getValue(result, Numero.CINCO.valueI))
						: 0);
				empProCue.setNombreProducto(Util.getValue(result, Numero.SEIS.valueI));

				empProdCueList.add(empProCue);
			}
		} else {
			throw new BusinessException(MessagesBussinesKey.KEY_SIN_ASOCIACION_COMISION_PRODUCTOS_EMPRESA.value);
		}
		return empProdCueList;
	}

	/**
	 * Metodo encargado de actualizar la asociacion de productos, comisiones y
	 * cuaentas a la empresa
	 * 
	 * @param DatosEmpresaProductoConfiguracionDTO contiene las listas con las
	 *                                             configuraciones a guardar
	 * @return
	 * @throws BusinessException
	 */
	public Boolean asociarConfigProductosEmpresas(DatosEmpresaProductoConfiguracionDTO productosEmpresaConf)
			throws BusinessException {

		// Se guarda la asociación de productos
		for (EmpresasProductosDTO prodEmpresa : productosEmpresaConf.getProductosConfEmpresa()) {
			guardarProductosConfEmpresa(prodEmpresa);
		}

		// Se guarda la asociación de comisiones
		for (EmpresasProductosComisionesDTO comProEmpresa : productosEmpresaConf.getComisionesConfEmpPro()) {
			guardarComisionesConfEmpPro(comProEmpresa);
		}

		// Se guarda la asociación de cuentas (opcional)
		for (CuentasProductosDTO cueProEmpresa : productosEmpresaConf.getCuentasConfigEmpPro()) {
			guardarCuentasConfigEmpPro(cueProEmpresa);
		}

		return true;
	}

	/**
	 * Metodo para almacenar la asociación de productos por empresa
	 * 
	 * @param productosConfEmpresa
	 */
	public void guardarProductosConfEmpresa(EmpresasProductosDTO productosConfEmpresa) {
		em.createNativeQuery(SQLConstant.UPDATE_ASOCIACION_EMPRESAS_PRODUCTOS)
				.setParameter("valorMinimo", productosConfEmpresa.getValorMinimo())
				.setParameter("valorMaximo", productosConfEmpresa.getValorMaximo())
				.setParameter("valorMaximoDia", productosConfEmpresa.getValorMaximoDia())
				.setParameter("horaInicioVenta", productosConfEmpresa.getHoraInicioVenta())
				.setParameter("horaFinalVenta", productosConfEmpresa.getHoraFinalVenta())
				.setParameter("idEmpresa", productosConfEmpresa.getIdEmpresa())
				.setParameter("idProducto", productosConfEmpresa.getIdProducto()).executeUpdate();
	}

	/**
	 * Metodo para almacenar la asociación de comisiones de productos por empresa
	 * 
	 * @param productosConfEmpresa
	 */
	public void guardarComisionesConfEmpPro(EmpresasProductosComisionesDTO comisionesConfEmpPro) {
		em.createNativeQuery(SQLConstant.UPDATE_ASOCIACION_EMPRESAS_PRODUCTOS_COMISIONES)
				.setParameter("porcentajeComision", comisionesConfEmpPro.getPorcentajeComision())
				.setParameter("valorFijoComision", comisionesConfEmpPro.getValorFijoComision())
				.setParameter("idEmpresa", comisionesConfEmpPro.getIdEmpresa())
				.setParameter("idProducto", comisionesConfEmpPro.getIdProducto())
				.setParameter("idComision", comisionesConfEmpPro.getIdComision()).executeUpdate();
	}

	/**
	 * Metodo para almacenar la asociación de cuentas contables de productos por
	 * empresa
	 * 
	 * @param productosConfEmpresa
	 */
	public void guardarCuentasConfigEmpPro(CuentasProductosDTO cuentasConfigEmpPro) {
		em.createNativeQuery(SQLConstant.UPDATE_ASOCIACION_CUENTAS_PRODUCTOS)
				.setParameter("cuentaAsociada", cuentasConfigEmpPro.getCuentaAsociada())
				.setParameter("idEmpresa", cuentasConfigEmpPro.getIdEmpresa())
				.setParameter("idProducto", cuentasConfigEmpPro.getIdProducto())
				.setParameter("idCuenta", cuentasConfigEmpPro.getIdCuenta()).executeUpdate();
	}
	
	/**
	 * Metodo para insertar los productos por empresa
	 * 
	 * @param productosEmpresa
	 */
	public Boolean insertarEmpresaProducto(EmpresasProductosDTO productosEmpresa) {
		em.createNativeQuery(SQLConstant.INSERT_EMPRESAS_PRODUCTOS)
		.setParameter("idEmpresa", productosEmpresa.getIdEmpresa())
		.setParameter("idProducto", productosEmpresa.getIdProducto())
		.setParameter("valorMinimo", productosEmpresa.getValorMinimo())
		.setParameter("valorMaximo", productosEmpresa.getValorMaximo())
		.setParameter("valorMaximoDia", productosEmpresa.getValorMaximoDia())
		.setParameter("idEstado", productosEmpresa.getIdEstado())
		.setParameter("horaInicioVenta", productosEmpresa.getHoraInicioVenta())
		.setParameter("horaFinalVenta", productosEmpresa.getHoraFinalVenta()).executeUpdate();
		
		return true;
	}
	
	/**
	 * Metodo para insertar comisiones de productos por empresa
	 * 
	 * @param comisionesEmpPro
	 */
	public Boolean insertarEmpresaProductoComisiones(EmpresasProductosComisionesDTO comisionesEmpPro) {
		em.createNativeQuery(SQLConstant.INSERT_EMPRESAS_PRODUCTOS_COMISIONES)
				.setParameter("idEmpresa", comisionesEmpPro.getIdEmpresa())
				.setParameter("idProducto", comisionesEmpPro.getIdProducto())
				.setParameter("idComision", comisionesEmpPro.getIdComision())
				.setParameter("porcentajeComision", comisionesEmpPro.getPorcentajeComision())
				.setParameter("valorFijoComision", comisionesEmpPro.getValorFijoComision())
				.setParameter("idEstado", comisionesEmpPro.getIdEstado()).executeUpdate();
		
		return true;
	}

	/**
	 * Metodo para insertar cuentas contables de productos por
	 * empresa
	 * 
	 * @param cuentaProducto
	 */
	public Boolean insertarCuentaProducto(CuentasProductosDTO cuentaProducto) {
		em.createNativeQuery(SQLConstant.INSERT_CUENTAS_PRODUCTOS)
				.setParameter("idEmpresa", cuentaProducto.getIdEmpresa())
				.setParameter("idProducto", cuentaProducto.getIdProducto())
				.setParameter("idCuenta", cuentaProducto.getIdCuenta())
				.setParameter("codCuenta", cuentaProducto.getCuentaAsociada()).executeUpdate();
		
		return true;
	}

}
