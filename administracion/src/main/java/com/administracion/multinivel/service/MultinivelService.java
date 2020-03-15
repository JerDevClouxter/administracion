package com.administracion.multinivel.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.administracion.constant.MessagesBussinesKey;
import com.administracion.constant.Numero;
import com.administracion.constant.SQLConstant;
import com.administracion.dto.EmpresasDTO;
import com.administracion.dto.multinivel.ChildrenDTO;
import com.administracion.dto.multinivel.CuentasProductosDTO;
import com.administracion.dto.multinivel.DatosEmpresaProductoConfiguracionDTO;
import com.administracion.dto.multinivel.EmpresasIdUsuarioDTO;
import com.administracion.dto.multinivel.EmpresasProductosComisionesDTO;
import com.administracion.dto.multinivel.EmpresasProductosDTO;
import com.administracion.repository.IEmpresasRepository;
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

				if (!esEditarConfiguracion) {
					if (empProducto.getValorMinimo() == null || empProducto.getValorMinimo() == 0.0) {
						empProductosList.add(empProducto);
					}

				} else if (esEditarConfiguracion) {
					if (empProducto.getValorMinimo() != null && empProducto.getValorMinimo() > 0.0) {
						empProductosList.add(empProducto);
					}
					
				}

			}
			if (!esEditarConfiguracion && empProductosList.isEmpty()) {
				throw new BusinessException(
						MessagesBussinesKey.KEY_ASOCIACION_EXISTENTE_PRODUCTOS_EMPRESA_SELECCIONADA.value);
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

}
