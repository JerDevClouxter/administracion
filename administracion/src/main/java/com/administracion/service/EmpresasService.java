package com.administracion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.administracion.builder.Builder;
import com.administracion.dto.EmpresasDTO;
import com.administracion.entity.Empresas;
import com.administracion.repository.IEmpresasRepository;
import com.administracion.util.BusinessException;

/**
 * Service que contiene los procesos de negocio para las EMPRESAS
 */
@Service
@Transactional(readOnly = true)
public class EmpresasService {

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
}
