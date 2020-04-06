package com.administracion.service;

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
import com.administracion.constant.SQLConstant;
import com.administracion.dto.multinivel.ChildrenDTO;
import com.administracion.dto.multinivel.ComisionesDTO;
import com.administracion.dto.multinivel.CuentasDTO;
import com.administracion.dto.multinivel.CuentasProductosDTO;
import com.administracion.dto.multinivel.DatosEmpresaProductoConfiguracionDTO;
import com.administracion.dto.multinivel.EmpresasIdUsuarioDTO;
import com.administracion.dto.multinivel.EmpresasProductosComisionesDTO;
import com.administracion.dto.multinivel.EmpresasProductosDTO;
import com.administracion.dto.multinivel.ProductosDTO;
import com.administracion.dto.transversal.EmpresasDTO;
import com.administracion.entity.Comisiones;
import com.administracion.entity.Cuentas;
import com.administracion.entity.Productos;
import com.administracion.enums.EstadoEnum;
import com.administracion.enums.Numero;
import com.administracion.repository.IComisionesRepository;
import com.administracion.repository.ICuentasRepository;
import com.administracion.repository.IProductosRepository;
import com.administracion.util.BusinessException;
import com.administracion.util.Util;

/**
 * Service que contiene los procesos de negocio para multinivel
 */
@Service
public class MultinivelService {

	/** Contexto de la persistencia del sistema */
	@PersistenceContext
	private EntityManager em;

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
	 * Metodo encargado de consultar las empresas padre por idUsuario
	 * 
	 * @param idUSuario
	 * @return List<EmpresasDTO>
	 * @throws BusinessException
	 */
	public List<EmpresasDTO> consultarEmpresasPadreIdUsuario(Long idUSuario) throws BusinessException {
		Query q = em.createNativeQuery(SQLConstant.SELECT_EMPRESAS_POR_ID_USAURIO).setParameter("idUSuario", idUSuario);
		List<Object[]> result = q.getResultList();
		EmpresasDTO empresaDTO = null;
		List<EmpresasDTO> empresasListDTO = new ArrayList<>();

		if (result != null && !result.isEmpty()) {
			for (Object[] data : result) {
				if (data[Numero.SEIS.valueI] == null) {
					empresaDTO = new EmpresasDTO();
					empresaDTO.setIdEmpresa(Long.valueOf(Util.getValue(data, Numero.ZERO.valueI)));
					empresaDTO.setNitEmpresa(Util.getValue(data, Numero.UNO.valueI));
					empresaDTO.setRazonSocial(Util.getValue(data, Numero.DOS.valueI));
					empresasListDTO.add(empresaDTO);
				}

			}

		} else {
			throw new BusinessException(MessagesBussinesKey.KEY_SIN_RELACION_EMPRESA_POR_IDUSUARIO.value);
		}

		return empresasListDTO;
	}

	/**
	 * Metodo encargado de consultar las empresas padre con sus respectivos hijos
	 * por idUsuario
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

				List<ChildrenDTO> listHija = new ArrayList<>();
				for (EmpresasDTO empresaHijaDTO : empresaHijaDTOList) {
					if (empresaHijaDTO.getIdEmpresaPadre().equals(empresasDTO.getIdEmpresa())) {
						ChildrenDTO hija = new ChildrenDTO();
						hija.setData(empresaHijaDTO);
						listHija.add(hija);
					}
					if (!listHija.isEmpty()) {
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
	 * metodo encargado de buscar los productos asociados a una empresa ya sea padre
	 * o hija
	 * 
	 * @param idEmpresa
	 * @return List<EmpresasProductosDTO>
	 * @throws BusinessException
	 */
	public List<EmpresasProductosDTO> consultarProductosIdEmpresa(Long idEmpresa, Long idEmpresaPadre,
			Boolean validarExistencia) throws BusinessException {

		if (idEmpresa != null && idEmpresaPadre != null) {
			List<EmpresasProductosDTO> listaProductosEmpreHija;
			List<EmpresasProductosDTO> listaProductosEmprePadre;
			// Consulta los productos asociados al padre, para saber cuales puede
			// comecializar la hija
			listaProductosEmprePadre = consultarProductosEmpresa(idEmpresaPadre, validarExistencia);

			existeComisionAsociadaProdPadre(listaProductosEmprePadre);

			// Consulto si ya hay productos configurados para la hija
			listaProductosEmpreHija = consultarProductosEmpresa(idEmpresa, false);
			/**
			 * verifico configurados y comparo contra los productos del padre para asegurar
			 * que muestra, configurados y los pendientes por asociar
			 */
			for (EmpresasProductosDTO empresasProductosPadre : listaProductosEmprePadre) {
				Boolean exiteRelacionPadreHija = false;
				for (EmpresasProductosDTO empresasProductosHija : listaProductosEmpreHija) {

					if (empresasProductosPadre.getIdProducto().equals(empresasProductosHija.getIdProducto())) {
						exiteRelacionPadreHija = true;
						break;
					}

				}
				// Se agregan las configuraciones de productos pendientes de asociar
				if (!exiteRelacionPadreHija) {
					EmpresasProductosDTO empresaProducto = new EmpresasProductosDTO();
					empresaProducto.setIdEmpresa(idEmpresa);
					empresaProducto.setIdProducto(empresasProductosPadre.getIdProducto());
					empresaProducto.setNombreProducto(empresasProductosPadre.getNombreProducto());
					empresaProducto.setValorMinimo(0.0);
					empresaProducto.setValorMaximo(0.0);
					empresaProducto.setValorMaximoDia(0.0);
					empresaProducto.setEsPrimerVez(true);
					listaProductosEmpreHija.add(empresaProducto);
				}

			}

			return listaProductosEmpreHija;
		} else {
			return consultarProductosEmpresa(idEmpresa, validarExistencia);
		}

	}

	
	private void existeComisionAsociadaProdPadre(List<EmpresasProductosDTO> listaProductosEmprePadre)
			throws BusinessException {

		if (listaProductosEmprePadre != null && !listaProductosEmprePadre.isEmpty()) {
			List<EmpresasProductosDTO> listaProdTemporal = new ArrayList<>();
			for (EmpresasProductosDTO empresasProductosDTO : listaProductosEmprePadre) {
				List<EmpresasProductosComisionesDTO> existeComision = consultarComisionProdEmpresa(
						empresasProductosDTO.getIdEmpresa(), empresasProductosDTO.getIdProducto(), false);
				if (existeComision.isEmpty()) {
					listaProdTemporal.add(empresasProductosDTO);
				}
			}
			if (!listaProdTemporal.isEmpty()) {
				listaProductosEmprePadre.removeAll(listaProdTemporal);
			}
		}
	}

	/**
	 * Metodo para consultar productos por idEmpresa
	 * 
	 * @param idEmpresa
	 * @param validarExistencia
	 * @return
	 * @throws BusinessException
	 */
	public List<EmpresasProductosDTO> consultarProductosEmpresa(Long idEmpresa, Boolean validarExistencia)
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
		} else if (validarExistencia) {
			throw new BusinessException(MessagesBussinesKey.KEY_SIN_ASOCIACION_PRODUCTOS_EMPRESA_SELECCIONADA.value);
		}

		return empProductosList;
	}

	/**
	 * metodo encargado de buscar las comisiones asociadas a los productos asociados
	 * a una empresa ay sea padre o hija
	 * 
	 * @param idEmpresa
	 * @param idProducto
	 * @return List<EmpresasProductosDTO>
	 * @throws BusinessException
	 */
	public List<EmpresasProductosComisionesDTO> consultarEmpresaProductosComision(Long idEmpresa, Long idEmpresaPadre,
			Long idProducto, Boolean validarExistencia) throws BusinessException {

		if (idEmpresa != null && idEmpresaPadre != null) {
			List<EmpresasProductosComisionesDTO> listaComisionesProduEmpreHija;
			List<EmpresasProductosComisionesDTO> listaProductosEmprePadre;
			// Consulta las comisones productos asociados al padre, para saber cuales puede
			// comecializar la hija
			listaProductosEmprePadre = consultarComisionProdEmpresa(idEmpresaPadre, idProducto, validarExistencia);

			// Consulto si ya hay comisones productos configurados para la hija
			listaComisionesProduEmpreHija = consultarComisionProdEmpresa(idEmpresa, idProducto, false);
			/**
			 * verifico configurados y comparo contra las comisones productos del padre para
			 * asegurar que muestra, configurados y las comisones pendientes por asociar
			 */
			for (EmpresasProductosComisionesDTO empresasComProductosPadre : listaProductosEmprePadre) {
				Boolean exiteRelacionPadreHija = false;
				for (EmpresasProductosComisionesDTO empresasComProductosHija : listaComisionesProduEmpreHija) {

					if (empresasComProductosPadre.getIdComision().equals(empresasComProductosHija.getIdComision())
							&& empresasComProductosPadre.getIdProducto()
									.equals(empresasComProductosHija.getIdProducto())) {

						empresasComProductosHija
								.setPorcentajeComisionPadre(empresasComProductosPadre.getPorcentajeComision());
						empresasComProductosHija
								.setValorFijoComisionPadre(empresasComProductosPadre.getValorFijoComision());
						exiteRelacionPadreHija = true;
						break;
					}

				}
				// Se agregan las configuraciones de las comisones productos pendientes de
				// asociar
				if (!exiteRelacionPadreHija) {
					EmpresasProductosComisionesDTO empresaComisionProducto = new EmpresasProductosComisionesDTO();
					empresaComisionProducto.setIdEmpresa(idEmpresa);
					empresaComisionProducto.setIdProducto(empresasComProductosPadre.getIdProducto());
					empresaComisionProducto.setIdComision(empresasComProductosPadre.getIdComision());
					empresaComisionProducto.setNombreProducto(empresasComProductosPadre.getNombreProducto());
					empresaComisionProducto
							.setPorcentajeComisionPadre(empresasComProductosPadre.getPorcentajeComision());
					empresaComisionProducto.setValorFijoComisionPadre(empresasComProductosPadre.getValorFijoComision());
					empresaComisionProducto.setPorcentajeComision(0.0);
					empresaComisionProducto.setValorFijoComision(0.0);
					empresaComisionProducto.setEsPrimerVez(true);
					listaComisionesProduEmpreHija.add(empresaComisionProducto);
				}

			}

			return listaComisionesProduEmpreHija;
		} else {
			return consultarComisionProdEmpresa(idEmpresa, idProducto, validarExistencia);
		}

	}

	/**
	 * Consultar comisiones producto empresa hija
	 * 
	 * @param idEmpresa
	 * @param idProducto
	 * @param validarExistencia
	 * @return
	 * @throws BusinessException
	 */
	public List<EmpresasProductosComisionesDTO> consultarComisionProdEmpresa(Long idEmpresa, Long idProducto,
			Boolean validarExistencia) throws BusinessException {
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
				empProCom.setNombreProducto(Util.getValue(result, Numero.CINCO.valueI));
				empProdComisionesList.add(empProCom);
			}
		} else if (validarExistencia) {
			throw new BusinessException(MessagesBussinesKey.KEY_SIN_ASOCIACION_COMISION_PRODUCTOS_EMPRESA.value);
		}
		return empProdComisionesList;
	}

	/**
	 * Consultar comisiones edición empresa padre
	 * 
	 * @param idEmpresa
	 * @return
	 * @throws BusinessException
	 */
	public List<EmpresasProductosComisionesDTO> consultarComisionProdEditarEmpresa(Long idEmpresa)
			throws BusinessException {
		List<EmpresasProductosComisionesDTO> empProdComisionesList = new ArrayList<>();
		Query q = em.createNativeQuery(SQLConstant.SELECT_COMISIONES_EDITAR_ID_EMPRESA).setParameter("idEmpresa",
				idEmpresa);
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
				empProCom.setNombreProducto(Util.getValue(result, Numero.CINCO.valueI));
				empProCom.setNombreComision(Util.getValue(result, Numero.SEIS.valueI));
				empProdComisionesList.add(empProCom);
			}
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
	public List<CuentasProductosDTO> consultarCuentasProductosEmpresa(Long idEmpresa, Long idEmpresaPadre,
			Long idProducto) throws BusinessException {

		if (idEmpresa != null && idEmpresaPadre != null) {
			List<CuentasProductosDTO> listaCuentasProduEmpreHija;
			List<CuentasProductosDTO> listaCuentasEmprePadre;
			// Consulta las comisones productos asociados al padre, para saber cuales puede
			// comecializar la hija
			listaCuentasEmprePadre = consultarCuentasProdEmp(idEmpresaPadre, idProducto);

			// Consulto si ya hay comisones productos configurados para la hija
			listaCuentasProduEmpreHija = consultarCuentasProdEmp(idEmpresa, idProducto);
			/**
			 * verifico configurados y comparo contra las comisones productos del padre para
			 * asegurar que muestra, configurados y las comisones pendientes por asociar
			 */
			for (CuentasProductosDTO empresasCueProductosPadre : listaCuentasEmprePadre) {
				Boolean exiteRelacionPadreHija = false;
				for (CuentasProductosDTO empresasCueProductosHija : listaCuentasProduEmpreHija) {

					if (empresasCueProductosPadre.getIdCuenta().equals(empresasCueProductosHija.getIdCuenta())
							&& empresasCueProductosPadre.getIdProducto()
									.equals(empresasCueProductosHija.getIdProducto())) {
						exiteRelacionPadreHija = true;
						break;
					}

				}
				// Se agregan las configuraciones de las comisones productos pendientes de
				// asociar
				if (!exiteRelacionPadreHija) {
					CuentasProductosDTO empresaCuentaProducto = new CuentasProductosDTO();
					empresaCuentaProducto.setIdEmpresa(idEmpresa);
					empresaCuentaProducto.setIdProducto(empresasCueProductosPadre.getIdProducto());
					empresaCuentaProducto.setNombreProducto(empresasCueProductosPadre.getNombreProducto());
					empresaCuentaProducto.setIdCuenta(empresasCueProductosPadre.getIdCuenta());
					empresaCuentaProducto.setEsPrimerVez(true);
					listaCuentasProduEmpreHija.add(empresaCuentaProducto);
				}

			}

			return listaCuentasProduEmpreHija;
		} else {
			return consultarCuentasProdEmp(idEmpresa, idProducto);
		}

	}

	/**
	 * Consultar cuentas producto empresa
	 * 
	 * @param idEmpresa
	 * @param idProducto
	 * @return
	 */
	private List<CuentasProductosDTO> consultarCuentasProdEmp(Long idEmpresa, Long idProducto) {
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
				empProCue.setNombreProducto(Util.getValue(result, Numero.CINCO.valueI));

				empProdCueList.add(empProCue);
			}
		}
		return empProdCueList;
	}

	/**
	 * Consultar cuentas producto empresa padre
	 * 
	 * @param idEmpresa
	 * @return
	 */
	public List<CuentasProductosDTO> consultarCuentasEditarEmpresa(Long idEmpresa) throws BusinessException {
		List<CuentasProductosDTO> empProdCueList = new ArrayList<>();
		Query q = em.createNativeQuery(SQLConstant.SELECT_CUENTAS_EDITAR_ID_EMPRESA).setParameter("idEmpresa",
				idEmpresa);
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
				empProCue.setNombreProducto(Util.getValue(result, Numero.CINCO.valueI));

				empProdCueList.add(empProCue);
			}
		}
		return empProdCueList;
	}

	/**
	 * Metodo encargado de actualizar la asociacion de productos, comisiones y
	 * cuaentas a la empresa
	 * 
	 * @param DatosEmpresaProductoConfiguracionDTO contiene las listas con las
	 *                                             configuraciones a guardar
	 * @throws BusinessException
	 */
	@Transactional
	public void asociarConfigProductosEmpresas(DatosEmpresaProductoConfiguracionDTO productosEmpresaConf)
			throws Exception {

		try {

			// Se guarda la asociación de productos
			if (productosEmpresaConf.getProductosConfEmpresa() != null
					&& productosEmpresaConf.getComisionesConfEmpPro() != null) {
				for (EmpresasProductosDTO prodEmpresa : productosEmpresaConf.getProductosConfEmpresa()) {
					guardarProductosConfEmpresa(prodEmpresa);
				}
			}

			// Se guarda la asociación de comisiones
			if (productosEmpresaConf.getComisionesConfEmpPro() != null) {
				for (EmpresasProductosComisionesDTO comProEmpresa : productosEmpresaConf.getComisionesConfEmpPro()) {
					guardarComisionesConfEmpPro(comProEmpresa);
				}
			}

			// Se guarda la asociación de cuentas (opcional)
			if (productosEmpresaConf.getCuentasConfigEmpPro() != null) {
				for (CuentasProductosDTO cueProEmpresa : productosEmpresaConf.getCuentasConfigEmpPro()) {
					if (cueProEmpresa.getCodCuenta() != null && !cueProEmpresa.getCodCuenta().equals("")) {
						guardarCuentasConfigEmpPro(cueProEmpresa);
					}

				}
			}

		} catch (Exception e) {
			em.getTransaction().rollback();
		}
	}

	/**
	 * Metodo para almacenar la asociación de productos por empresa
	 * 
	 * @param productosConfEmpresa
	 */
	@Transactional
	public void guardarProductosConfEmpresa(EmpresasProductosDTO productosConfEmpresa) throws BusinessException {
		if (productosConfEmpresa.getEsPrimerVez() != null && productosConfEmpresa.getEsPrimerVez()) {
			insertarEmpresaProducto(productosConfEmpresa);
		} else {
			editarProductosEmpresa(productosConfEmpresa);
		}

	}

	/**
	 * Metodo encargado de enviar los productos para editar los productos de una
	 * empresa padre
	 * 
	 * @param productosConfEmpresa
	 * @throws BusinessException
	 */
	@Transactional
	public void editarProductosConfEmpPadre(DatosEmpresaProductoConfiguracionDTO productosConfEmpresa)
			throws BusinessException {

		for (EmpresasProductosDTO empresasProductosDTO : productosConfEmpresa.getProductosConfEmpresa()) {
			editarProductosEmpresa(empresasProductosDTO);
		}

	}

	/**
	 * Metodo encargado de editar los productos de una empresa padre
	 * 
	 * @param productosConfEmpresa
	 * @throws BusinessException
	 */
	@Transactional
	public void editarProductosEmpresa(EmpresasProductosDTO empresasProductosDTO) {
		em.createNativeQuery(SQLConstant.UPDATE_ASOCIACION_EMPRESAS_PRODUCTOS)
				.setParameter("valorMinimo", empresasProductosDTO.getValorMinimo())
				.setParameter("valorMaximo", empresasProductosDTO.getValorMaximo())
				.setParameter("valorMaximoDia", empresasProductosDTO.getValorMaximoDia())
				.setParameter("horaInicioVenta", empresasProductosDTO.getHoraInicioVenta())
				.setParameter("horaFinalVenta", empresasProductosDTO.getHoraFinalVenta())
				.setParameter("idEmpresa", empresasProductosDTO.getIdEmpresa())
				.setParameter("idProducto", empresasProductosDTO.getIdProducto()).executeUpdate();
	}

	/**
	 * Metodo para almacenar la asociación de comisiones de productos por empresa
	 * 
	 * @param productosConfEmpresa
	 */
	@Transactional
	public void guardarComisionesConfEmpPro(EmpresasProductosComisionesDTO comisionesConfEmpPro)
			throws BusinessException {
		if (comisionesConfEmpPro.getEsPrimerVez() != null && comisionesConfEmpPro.getEsPrimerVez()) {
			insertarEmpresaProductoComisiones(comisionesConfEmpPro);
		} else {
			editarComiProdEmpresa(comisionesConfEmpPro);
		}

	}

	/**
	 * Metodo encargado de enviar a editar las comisiones producto empresa padre
	 * 
	 * @param productosConfEmpresa
	 * @throws BusinessException
	 */
	@Transactional
	public void editarComProdConfEmpPadre(DatosEmpresaProductoConfiguracionDTO productosConfEmpresa)
			throws BusinessException {

		for (EmpresasProductosComisionesDTO empresasComisProductosDTO : productosConfEmpresa
				.getComisionesConfEmpPro()) {
			editarComiProdEmpresa(empresasComisProductosDTO);
		}

	}

	/**
	 * Metodo encargado de editar las comisiones producto de una empresa padre
	 * 
	 * @param comisionesConfEmpPro
	 */
	@Transactional
	public void editarComiProdEmpresa(EmpresasProductosComisionesDTO comisionesConfEmpPro) {
		em.createNativeQuery(SQLConstant.UPDATE_ASOCIACION_EMPRESAS_PRODUCTOS_COMISIONES).setParameter(
				"porcentajeComision",
				comisionesConfEmpPro.getPorcentajeComision() != null ? comisionesConfEmpPro.getPorcentajeComision() : 0)
				.setParameter("valorFijoComision",
						comisionesConfEmpPro.getValorFijoComision() != null
								? comisionesConfEmpPro.getValorFijoComision()
								: 0)
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
	@Transactional
	public void guardarCuentasConfigEmpPro(CuentasProductosDTO cuentasConfigEmpPro) throws BusinessException {

		if (cuentasConfigEmpPro.getEsPrimerVez() != null && cuentasConfigEmpPro.getEsPrimerVez()) {
			insertarCuentaProducto(cuentasConfigEmpPro);
		} else {
			editarCuentProdEmpresa(cuentasConfigEmpPro);
		}

	}

	/**
	 * Metodo encargo de enviar las cuentas producto a editar
	 * 
	 * @param productosConfEmpresa
	 * @throws BusinessException
	 */
	@Transactional
	public void editarCuenProdConfEmpPadre(DatosEmpresaProductoConfiguracionDTO productosConfEmpresa)
			throws BusinessException {

		for (CuentasProductosDTO empresasComisProductosDTO : productosConfEmpresa.getCuentasConfigEmpPro()) {
			editarCuentProdEmpresa(empresasComisProductosDTO);
		}

	}

	/**
	 * Metodo encargado de editar las cuentas productos empresa padre
	 * 
	 * @param cuentasConfigEmpPro
	 */
	@Transactional
	public void editarCuentProdEmpresa(CuentasProductosDTO cuentasConfigEmpPro) {
		em.createNativeQuery(SQLConstant.UPDATE_ASOCIACION_CUENTAS_PRODUCTOS)
				.setParameter("idEmpresa", cuentasConfigEmpPro.getIdEmpresa())
				.setParameter("idProducto", cuentasConfigEmpPro.getIdProducto())
				.setParameter("idCuenta", cuentasConfigEmpPro.getIdCuenta())
				.setParameter("codCuenta", cuentasConfigEmpPro.getCodCuenta()).executeUpdate();
	}

	/**
	 * Metodo para insertar los productos por empresa
	 * 
	 * @param productosEmpresa
	 */
	@Transactional
	public void insertarEmpresaProducto(EmpresasProductosDTO productosEmpresa) throws BusinessException {

		List<EmpresasProductosDTO> poductosEmpresaList = consultarProductosIdEmpresa(productosEmpresa.getIdEmpresa(),
				null, false);

		if (poductosEmpresaList != null && !poductosEmpresaList.isEmpty()) {

			for (EmpresasProductosDTO empresasProductosDTO : poductosEmpresaList) {
				if (empresasProductosDTO.getIdProducto().equals(productosEmpresa.getIdProducto())) {
					throw new BusinessException(MessagesBussinesKey.KEY_CONFIGURACION_PRODUCTO_EMPRESA_EXISTENTE.value);
				}
			}
		}
		em.createNativeQuery(SQLConstant.INSERT_EMPRESAS_PRODUCTOS)
				.setParameter("idEmpresa", productosEmpresa.getIdEmpresa())
				.setParameter("idProducto", productosEmpresa.getIdProducto())
				.setParameter("valorMinimo", productosEmpresa.getValorMinimo())
				.setParameter("valorMaximo", productosEmpresa.getValorMaximo())
				.setParameter("valorMaximoDia", productosEmpresa.getValorMaximoDia())
				.setParameter("idEstado", EstadoEnum.ACTIVO.name())
				.setParameter("horaInicioVenta", productosEmpresa.getHoraInicioVenta())
				.setParameter("horaFinalVenta", productosEmpresa.getHoraFinalVenta()).executeUpdate();

	}

	/**
	 * Metodo para insertar comisiones de productos por empresa
	 * 
	 * @param comisionesEmpPro
	 */
	@Transactional
	public void insertarEmpresaProductoComisiones(EmpresasProductosComisionesDTO comisionesEmpPro)
			throws BusinessException {

		List<EmpresasProductosComisionesDTO> comisionPoduEmpresaList = consultarEmpresaProductosComision(
				comisionesEmpPro.getIdEmpresa(), null, comisionesEmpPro.getIdProducto(), false);

		if (comisionPoduEmpresaList != null && !comisionPoduEmpresaList.isEmpty()) {

			for (EmpresasProductosComisionesDTO empresasComisionProductosDTO : comisionPoduEmpresaList) {
				if (empresasComisionProductosDTO.getIdProducto().equals(comisionesEmpPro.getIdProducto())
						|| empresasComisionProductosDTO.getIdComision().equals(comisionesEmpPro.getIdComision())) {
					throw new BusinessException(
							MessagesBussinesKey.KEY_CONFIGURACION_COMISION_PRODUCTO_EMPRESA_EXISTENTE.value);
				}
			}
		}
		em.createNativeQuery(SQLConstant.INSERT_EMPRESAS_PRODUCTOS_COMISIONES)
				.setParameter("idEmpresa", comisionesEmpPro.getIdEmpresa())
				.setParameter("idProducto", comisionesEmpPro.getIdProducto())
				.setParameter("idComision", comisionesEmpPro.getIdComision())
				.setParameter("porcentajeComision",
						comisionesEmpPro.getPorcentajeComision() != null ? comisionesEmpPro.getPorcentajeComision() : 0)
				.setParameter("valorFijoComision",
						comisionesEmpPro.getValorFijoComision() != null ? comisionesEmpPro.getValorFijoComision() : 0)
				.setParameter("idEstado", EstadoEnum.ACTIVO.name()).executeUpdate();

	}

	/**
	 * Metodo para insertar cuentas contables de productos por empresa
	 * 
	 * @param cuentaProducto
	 */
	@Transactional
	public void insertarCuentaProducto(CuentasProductosDTO cuentaProducto) throws BusinessException {

		List<CuentasProductosDTO> comisionPoduEmpresaList = consultarCuentasProductosEmpresa(
				cuentaProducto.getIdEmpresa(), null, cuentaProducto.getIdProducto());

		if (comisionPoduEmpresaList != null && !comisionPoduEmpresaList.isEmpty()) {

			for (CuentasProductosDTO empresasCuentaProductosDTO : comisionPoduEmpresaList) {
				if (empresasCuentaProductosDTO.getIdProducto().equals(cuentaProducto.getIdProducto())
						|| empresasCuentaProductosDTO.getIdCuenta().equals(cuentaProducto.getIdCuenta())) {
					throw new BusinessException(
							MessagesBussinesKey.KEY_CONFIGURACION_CUENTA_PRODUCTO_EMPRESA_EXISTENTE.value);
				}
			}
		}

		em.createNativeQuery(SQLConstant.INSERT_CUENTAS_PRODUCTOS)
				.setParameter("idEmpresa", cuentaProducto.getIdEmpresa())
				.setParameter("idProducto", cuentaProducto.getIdProducto())
				.setParameter("idCuenta", cuentaProducto.getIdCuenta())
				.setParameter("codCuenta", cuentaProducto.getCodCuenta().trim()).executeUpdate();

	}

}
