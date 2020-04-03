package com.administracion.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.administracion.builder.Builder;
import com.administracion.constant.MessagesBussinesKey;
import com.administracion.enums.Numero;
import com.administracion.constant.SQLConstant;
import com.administracion.dto.transversal.EmpresasDTO;
import com.administracion.dto.multinivel.ProductosDTO;
import com.administracion.entity.Empresas;
import com.administracion.repository.IEmpresasRepository;
import com.administracion.util.BusinessException;
import com.administracion.util.Util;

/**
 * Service que contiene los procesos de negocio para las EMPRESAS
 */
@Service
public class EmpresasService {

	/** Contexto de la persistencia del sistema */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Repository que contiene los metodos utilitarios para la persistencia de la
	 * entidad EMPRESAS
	 */
	@Autowired
	private IEmpresasRepository empresaRepository;

	public EmpresasDTO findByIdEmpresa(Long idEmpresa) throws BusinessException {
		Empresas empresa = empresaRepository.findByIdEmpresa(idEmpresa);
		if (empresa != null) {
			Builder<Empresas, EmpresasDTO> buildEmpresaDTO = new Builder<Empresas, EmpresasDTO>(EmpresasDTO.class);
			return buildEmpresaDTO.copy(empresa);
		} else {
			throw new BusinessException(MessagesBussinesKey.KEY_SIN_RELACION_EMPRESA_POR_IDUSUARIO.value);
		}
	}

	/**
	 * Metodo encargado de consultar las empresas para exponer
	 * @return
	 */
	public List<EmpresasDTO> consultarEmpresas() throws BusinessException {
		Query q=em.createNativeQuery(SQLConstant.SELECT_EMPRESAS_BASE);
		List<Object[]> result = q.getResultList();
		EmpresasDTO empresaDTO=null;
		List<EmpresasDTO> empresaDTOList=new ArrayList<>();
		if (result != null && !result.isEmpty()) {
			for (Object[] data : result) {
				empresaDTO=new EmpresasDTO();
				empresaDTO.setIdEmpresa(Long.valueOf(Util.getValue(data, Numero.ZERO.valueI)));
				empresaDTO.setNitEmpresa(Util.getValue(data, Numero.UNO.valueI));
				empresaDTO.setRazonSocial(Util.getValue(data, Numero.DOS.valueI));
				empresaDTOList.add(empresaDTO);
			}
		}
		return empresaDTOList;
	}

	/**
	 * metodo encargado de filtrar los productos para una empresa
	 * @param idEmpresa
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	public List<ProductosDTO> consultarProductosEmpresas(Long idEmpresa)  throws BusinessException {
		 List<ProductosDTO> lista=new ArrayList<>();
		Query q=em.createNativeQuery(SQLConstant.SELECT_PRODUCTOS_EMPRESA_BASE)
				.setParameter("idEmpresa", idEmpresa);
		List<Object[]> listaT=q.getResultList();
		ProductosDTO productosDTO=null;
		if(listaT!=null && !listaT.isEmpty()) {
		for (Object[] result : listaT) {
			productosDTO=new ProductosDTO();
			productosDTO.setIdProducto(Long.valueOf(Util.getValue(result, Numero.ZERO.valueI)));
			productosDTO.setNombre(Util.getValue(result, Numero.UNO.valueI));
			lista.add(productosDTO);
		}
		}
		return lista;
	}
}
