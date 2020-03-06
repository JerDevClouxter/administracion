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
import com.administracion.constant.Numero;
import com.administracion.constant.SQLConstant;
import com.administracion.dto.EmpresasDTO;
import com.administracion.dto.ProductosDTO;
import com.administracion.entity.Empresas;
import com.administracion.repository.IEmpresasRepository;
import com.administracion.util.BusinessException;
import com.administracion.util.Util;

/**
 * Service que contiene los procesos de negocio para las EMPRESAS
 */
@Service
@Transactional(readOnly = true)
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

	public List<EmpresasDTO> findAll() throws Exception {
		List<EmpresasDTO> lstEmpresasDTO = null;
		Builder<Empresas, EmpresasDTO> builder = new Builder<Empresas, EmpresasDTO>(EmpresasDTO.class);
		List<Empresas> lstEmpresas = this.empresaRepository.findAll();
		if (!lstEmpresas.isEmpty()) {
			lstEmpresasDTO = builder.copy(lstEmpresas);
		}
		return lstEmpresasDTO;
	}

	@Transactional
	public Empresas save(EmpresasDTO empresaDTO) {
		Builder<EmpresasDTO, Empresas> builder = new Builder<EmpresasDTO, Empresas>(Empresas.class);
		return this.empresaRepository.save(builder.copy(empresaDTO));
	}

	//Pendiente definir manejo de onjetos con DTO, para no retornar la entidad
	@Transactional(readOnly = true)
	public Empresas findByIdEmpresa(Long idEmpresa) throws BusinessException {

		Empresas empresa = empresaRepository.findByIdEmpresa(idEmpresa);

		if (empresa != null) {
			return empresa;
		}
		// Pendiente estandarizar manejo de errores
		throw new BusinessException("No se encontro data");

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
