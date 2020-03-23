package com.administracion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.administracion.builder.Builder;
import com.administracion.dto.CuentasDTO;
import com.administracion.entity.Cuentas;
import com.administracion.repository.ICuentasRepository;

/**
 * Service que contiene los procesos de negocio para las CUENTAS
 */
@Service
@Transactional(readOnly = true)
public class CuentasService {

	/** Repository que contiene los metodos utilitarios para la persistencia de la entidad CUENTAS */
	@Autowired
	private ICuentasRepository cuentaRepository;
	
	public List<CuentasDTO> findAll() {
		List<CuentasDTO> lstCuentasDTO = null;
		Builder<Cuentas, CuentasDTO> builder = new Builder<Cuentas, CuentasDTO>(CuentasDTO.class);
		List<Cuentas> lstCuentas = this.cuentaRepository.findAll();
		if (!lstCuentas.isEmpty()) {
			lstCuentasDTO = builder.copy(lstCuentas);
		}
		return lstCuentasDTO;
	}
	
	@Transactional
	public Cuentas save(CuentasDTO cuentaDTO) {
		Builder<CuentasDTO, Cuentas> builder = 
				new Builder<CuentasDTO, Cuentas>(Cuentas.class);
		return this.cuentaRepository.save(builder.copy(cuentaDTO));		
	}
	
}
