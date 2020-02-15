package com.administracion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.administracion.builder.Builder;
import com.administracion.dto.EmpresaDTO;
import com.administracion.entity.Empresas;
import com.administracion.repository.EmpresasRepository;

/**
 * Service que contiene los procesos de negocio para las EMPRESAS
 */
@Service
@Transactional(readOnly = true)
public class EmpresasService {

	/** Repository que contiene los metodos utilitarios para la persistencia de la entidad EMPRESAS */
	@Autowired
	private EmpresasRepository empresaRepository;
	
	public List<EmpresaDTO> findAll() {
		List<EmpresaDTO> lstEmpresasDTO = null;
		Builder<Empresas, EmpresaDTO> builder = new Builder<Empresas, EmpresaDTO>(EmpresaDTO.class);
		List<Empresas> lstEmpresas = this.empresaRepository.findAll();
		if (!lstEmpresas.isEmpty()) {
			lstEmpresasDTO = builder.copy(lstEmpresas);
		}
		return lstEmpresasDTO;
	}
	
	@Transactional
	public Empresas save(Empresas empresa) {
		return this.empresaRepository.save(empresa);
	}
	
}
